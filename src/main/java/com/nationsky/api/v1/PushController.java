package com.nationsky.api.v1;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.model.Message;
import com.nationsky.model.P12;
import com.nationsky.push.Pushing;
import com.nationsky.service.DeviceTokenManager;
import com.nationsky.service.MessageManager;
import com.nationsky.service.P12Manager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/push")
@Scope("prototype")
public class PushController extends BaseFormController {
	@Autowired
	private P12Manager p12Manager;

	@Autowired
	private MessageManager messageManager;

	@Autowired
	private DeviceTokenManager deviceTokenManager;

	@Autowired
	private Root root;

	@RequestMapping(method = RequestMethod.POST, value = "/p12/{appId}")
	public @ResponseBody Root push(@PathVariable Long appId, @RequestParam Long p12Id, Message message) {
		if (p12Id == null) {
			root.setMessage(1, "P12证书文件不能为空");
		} else if (appId == null) {
			root.setMessage(2, "应用版本ID不能为空");
		} else if (message == null || message.equals("")) {
			root.setMessage(3, "推送消息内容不能为空");
		} else if (message.getAlert() == null || message.getAlert().equals("")) {
			root.setMessage(4, "推送消息内容不能为空");
		} else {
			if (message.getBadge() == null) {
				message.setBadge(1);
			} 
			if (message.getSound() == null || message.getSound().equals("")) {
				message.setSound("default");
			}
			/*
			 * 根据p12的ID获取p12信息
			 */
			P12 p12 = p12Manager.get(p12Id);
			String p12FilePath = p12.getFilepath();
			String p12Password = p12.getPassword();

			/*
			 * 根据appId获取DeviceToken
			 */
			List<String> tokenList = deviceTokenManager.getTokenByAppId(appId);

			try {
				/*
				 * 消息推送
				 */
				Pushing.send(message, p12FilePath, p12Password, tokenList);

				/*
				 * 推送过的消息入库保存
				 */
				AppstoreEdition appstoreEdition = new AppstoreEdition();
				appstoreEdition.setId(appId);
				message.setAppstoreEdition(appstoreEdition);
				message.setCreateTime(new Date());
				messageManager.save(message);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				root.setError("消息推送失败");
			}
		}
		return root;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{appId}")
	public @ResponseBody Root pushMsg(@PathVariable Long appId, Message message) {
		List<P12> p12List = p12Manager.getAllByAppId(appId);
		if (p12List == null || p12List.size() <= 0) {
			root.setMessage(1, "没有找到证书文件P12");
		} else {
			P12 p12 = p12Manager.getAllByAppId(appId).get(0);
			if (p12 == null || p12.getFilepath() == null) {
				root.setMessage(1, "P12证书文件不能为空");
			} else if (appId == null) {
				root.setMessage(2, "应用版本ID不能为空");
			} else if (message == null || message.equals("")) {
				root.setMessage(3, "推送消息内容不能为空");
			} else if (message.getAlert() == null || message.getAlert().equals("")) {
				root.setMessage(4, "推送消息内容不能为空");
			} else {
				if (message.getBadge() == null) {
					message.setBadge(1);
				} 
				if (message.getSound() == null || message.getSound().equals("")) {
					message.setSound("default");
				} 
				
				/*
				 * 根据p12的ID获取p12信息
				 */
				String p12FilePath = p12.getFilepath();
				String p12Password = p12.getPassword();

				/*
				 * 根据appId获取DeviceToken
				 */
				List<String> tokenList = deviceTokenManager.getTokenByAppId(appId);

				try {
					/*
					 * 消息推送
					 */
					Pushing.send(message, p12FilePath, p12Password, tokenList);

					/*
					 * 推送过的消息入库保存
					 */
					AppstoreEdition appstoreEdition = new AppstoreEdition();
					appstoreEdition.setId(appId);
					message.setAppstoreEdition(appstoreEdition);
					message.setCreateTime(new Date());
					messageManager.save(message);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					root.setError("消息推送失败");
				}
			}
		}
		return root;
	}
}
