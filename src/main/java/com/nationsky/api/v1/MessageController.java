package com.nationsky.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.service.MessageManager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/message")
@Scope("prototype")
public class MessageController extends BaseFormController {
	@Autowired
	private MessageManager messageManager = null;

	@Autowired
	private Root root;

	/**
	 * 根据应用版本ID查询该应用推送消息记录
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/list/{app_version_id}")
	public @ResponseBody
	Root getAllByAppId(@PathVariable Long app_version_id) {
		root.setObject(messageManager.getAllByAppId(app_version_id));
		return root;
	}

	/**
	 * 根据消息ID查看消息详情
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody
	Root getById(@PathVariable Long id) {
		root.setObject(messageManager.get(id));
		return root;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	Root delete(@PathVariable Long id) {
		messageManager.remove(id);
		return root;
	}
}
