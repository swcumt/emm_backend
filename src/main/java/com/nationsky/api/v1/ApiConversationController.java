package com.nationsky.api.v1;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.Conversation;
import com.nationsky.model.FrontUser;
import com.nationsky.model.Users;
import com.nationsky.service.ConversationManager;
import com.nationsky.vo.ConversationVO;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/conversation*")
@Scope("prototype")
public class ApiConversationController extends BaseFormController {

	private ConversationManager conversationManager;
	@Autowired
	public void setConversationManager(ConversationManager conversationManager) {
		this.conversationManager = conversationManager;
	}
	@Autowired
	private Root root;
	
	@RequestMapping(method=RequestMethod.GET,value="/{userId}")
    public @ResponseBody Root list(@PathVariable String userId){
		try {
			List<Conversation> commentList = conversationManager.findConvesation(userId);
			for (Conversation conversation : commentList) {
				FrontUser userA = conversation.getUserA();
				FrontUser userB = conversation.getUserB();
				userA.setPassword("");
				userB.setPassword("");
			}
	    	root.setObject(commentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return root;
    }
	
	/**
	 * 详细信息
	 * 
	 * @param id  会话id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	public @ResponseBody
	Root getDetail(@PathVariable String id) {
		Conversation conversation = new Conversation();
		ConversationVO conversationVO = new ConversationVO();
		if (!StringUtils.isBlank(id)) {
			try {
				conversation = conversationManager.get(new Long(id));
				conversationVO.setId(conversation.getId());
				conversationVO.setUserId1(conversation.getUserA().getId());
				conversationVO.setUserId2(conversation.getUserB().getId());
				conversationVO.setUpdateTime(conversation.getCreateTime().getTime());
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}

		root.setObject(conversationVO);
		return root;
	}
}
