package com.nationsky.api.v1;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nationsky.entity.Root;
import com.nationsky.model.ChatRecord;
import com.nationsky.model.Conversation;
import com.nationsky.model.FrontUser;
import com.nationsky.model.Users;
import com.nationsky.model.VendorDevice;
import com.nationsky.service.ChatRecordManager;
import com.nationsky.service.ConversationManager;
import com.nationsky.service.FrontUserManager;
import com.nationsky.service.VendorDeviceManager;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.PushUtil;

@Controller
@RequestMapping("/v1/chatRecord*")
@Scope("prototype")
public class ApiChatRecordController extends BaseFormController {

	private ChatRecordManager chatRecordManager;
	private ConversationManager conversationManager;
	private VendorDeviceManager vendorDeviceManager;
	private FrontUserManager frontUserManager;
	@Autowired
	public void setChatRecordManager(ChatRecordManager chatRecordManager) {
		this.chatRecordManager = chatRecordManager;
	}
	@Autowired
	public void setConversationManager(ConversationManager conversationManager) {
		this.conversationManager = conversationManager;
	}
	@Autowired
	public void setVendorDeviceManager(VendorDeviceManager vendorDeviceManager) {
		this.vendorDeviceManager = vendorDeviceManager;
	}
	@Autowired
	public void setFrontUserManager(FrontUserManager frontUserManager) {
		this.frontUserManager = frontUserManager;
	}

	@Autowired
	private Root root;
	
	/**
	 * 添加聊天记录
	 * @param chatRecord
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public @ResponseBody
	Root add(String fromUserId, String toUserId, String record,String uuid) {
		try{
			Date date = new Date();
			Conversation conversation = conversationManager.getConsersation(fromUserId,toUserId);
			if(conversation == null){
				conversation = new Conversation();
				FrontUser userA = new FrontUser();
				FrontUser userB = new FrontUser();
				userA.setId(fromUserId);
				userB.setId(toUserId);
				conversation.setUserA(userA);
				conversation.setUserB(userB);
				conversation.setCreateTime(date);
				conversation = conversationManager.save(conversation);
			}else{
				conversation.setCreateTime(date);
				conversation = conversationManager.save(conversation);
			}
			ChatRecord chatRecord = new ChatRecord();
			FrontUser users = new FrontUser();
			users.setId(fromUserId);
			chatRecord.setUsers(users);
			chatRecord.setRecord(record);
			chatRecord.setUuid(uuid);
			chatRecord.setConversationId(conversation.getId());
			chatRecord.setCreateTime(date);
			chatRecord.setIsRead("0");
			ChatRecord chatRecords = chatRecordManager.save(chatRecord);
			if (chatRecords == null) {
				root.setMessage(1, "发送失败");
			} else {
				conversation.uuid = uuid;
				root.setObject(conversation);
//				List<VendorDevice> venList = vendorDeviceManager.findDevicesByUserId(toUserId);
				FrontUser frontUser = frontUserManager.get(toUserId);
				String devicesId = frontUser.getId();
				JSONObject msgJson = new JSONObject();
				msgJson.put("msg", "你有一条新消息");
				msgJson.put("type", "messageList");
				msgJson.put("conversationId", conversation.getId());
				PushUtil.pushServer(msgJson.toString(), devicesId);
				root.setMessage("发送成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
			root.setMessage(1, "发送失败");
		}
		
		return root;
	}
	
	/**
	 * 获取某两个人的所有聊天记录
	 * @param conversationId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/all")
	public @ResponseBody
	Root getRecordListByConversation(String fromUserId, String toUserId) {
		Conversation consersation = conversationManager.getConsersation(fromUserId, toUserId);
		if(consersation != null){
			try {
				List<ChatRecord> chatRecordList = chatRecordManager.getRecordListByConversation(consersation.getId().toString());
				for (ChatRecord chatRecord : chatRecordList) {
					FrontUser users = chatRecord.getUsers();
					users.setPassword("");
					chatRecord.setUsers(users);
				}
				root.setObject(chatRecordList);
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}else{
			root.setMessage(2,"此两个人没有创建过会话");
		}
		return root;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/page/{fromUserId}/{toUserId}/{index}")
	public @ResponseBody
	Root getRecordPageListByConversation(@PathVariable String fromUserId,@PathVariable String toUserId, @PathVariable String index) {
		Conversation consersation = conversationManager.getConsersation(fromUserId, toUserId);
		if (consersation != null) {
			try {
				List<ChatRecord> chatRecordList = chatRecordManager.getRecordPageListByConversation(consersation.getId(),index);
				for (ChatRecord chatRecord : chatRecordList) {
					FrontUser users = chatRecord.getUsers();
					users.setPassword("");
					chatRecord.setUsers(users);
				}
				root.setObject(chatRecordList);
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}else{
			root.setMessage(2,"此两个人没有创建过会话");
		}
		
		return root;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/conversation/{conversationId}/{index}")
	public @ResponseBody
	Root getRecordPageListByConversation(@PathVariable String conversationId, @PathVariable String index) {
		if (!StringUtils.isBlank(conversationId)) {
			try {
				List<ChatRecord> chatRecordList = chatRecordManager.getRecordPageListByConversation(conversationId,index);
				for (ChatRecord chatRecord : chatRecordList) {
					FrontUser users = chatRecord.getUsers();
					users.setPassword("");
					chatRecord.setUsers(users);
				}
				root.setObject(chatRecordList);
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}else{
			root.setMessage(2,"参数错误");
		}
		
		return root;
	}
	
	/**
	 * 获取最新聊天记录
	 * @param conversationId  会话id
	 * @param time			     记录最后时间戳
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/time/{conversationId}/{time}")
	public @ResponseBody
	Root getRecordListByTime(@PathVariable String conversationId, @PathVariable String time) {
		if (!StringUtils.isBlank(conversationId)) {
			try {
				List<ChatRecord> chatRecordList = chatRecordManager.getRecordListByTime(conversationId,time);
				for (ChatRecord chatRecord : chatRecordList) {
					FrontUser users = chatRecord.getUsers();
					users.setPassword("");
					chatRecord.setUsers(users);
				}
				root.setObject(chatRecordList);
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}else{
			root.setMessage(2,"参数错误");
		}
		
		return root;
	}
	/**
	 * 修改最新聊天记录为已读状态
	 * @param fromUserId  发送人id
	 * @param toUserId  接收人id
	 * @param time			     记录最后时间戳
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/update/{conversationId}/{fromUserId}/{time}")
	public @ResponseBody
	Root getRecordByTime(@PathVariable String conversationId,@PathVariable String fromUserId, @PathVariable String time) {
		if (!StringUtils.isBlank(conversationId) && !StringUtils.isBlank(fromUserId)) {
			try {
				Conversation conversation = conversationManager.get(Long.valueOf(conversationId));
				String toUserId = null;
				if(conversation.getUserA().getId().equals(fromUserId)){
					toUserId = conversation.getUserB().getId();
				}else{
					toUserId = conversation.getUserA().getId();
				}
				chatRecordManager.updateRecordStatus(conversationId,toUserId,time);
				root.setError("修改状态成功");
			} catch (Exception e) {
				root.setError("修改状态失败");
			}
		}else{
			root.setMessage(2,"参数错误");
		}
		
		return root;
	}
	/**
	 * 获取时间段内的记录
	 * @param conversationId	会话id
	 * @param fromTime			开始时间
	 * @param toTime			结束时间
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/timeslot/{conversationId}/{fromTime}/{toTime}")
	public @ResponseBody
	Root getRecordListByTimeSlot(@PathVariable String conversationId, @PathVariable String fromTime, @PathVariable String toTime) {
		if (!StringUtils.isBlank(conversationId)) {
			try {
				List<ChatRecord> chatRecordList = chatRecordManager.getRecordListByTimeSlot(conversationId,fromTime,toTime);
				for (ChatRecord chatRecord : chatRecordList) {
					FrontUser users = chatRecord.getUsers();
					users.setPassword("");
					chatRecord.setUsers(users);
				}
				root.setObject(chatRecordList);
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}else{
			root.setMessage(2,"此两个人没有创建过会话");
		}
		
		return root;
	}
	
	/**
	 * 通过记录id获取聊天内容
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/detail/{id}")
    public @ResponseBody  Root detail(@PathVariable String id){
		ChatRecord chatRecord = null;
    	  if (!StringUtils.isBlank(id)) {
    		  try {
    			  chatRecord =  chatRecordManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
          }
    	  root.setObject(chatRecord);
		return root;
    }
	
}
