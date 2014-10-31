package com.nationsky.api.v1;

import java.util.Date;

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
import com.nationsky.model.DeviceToken;
import com.nationsky.service.DeviceTokenManager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/deviceToken")
@Scope("prototype")
public class DeviceTokenController extends BaseFormController {
	@Autowired
	private DeviceTokenManager deviceTokenManager;

	@Autowired
	private Root root;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Root save(@RequestParam Long appVersionId, @RequestParam String token) {
		
		if (appVersionId == null || token == null || token.equals("")) {
			root.setError("参数不符!");
		} else {
			AppstoreEdition appstoreEdition = new AppstoreEdition();
			appstoreEdition.setId(appVersionId);

			DeviceToken deviceToken = new DeviceToken();
			deviceToken.setAppstoreEdition(appstoreEdition);
			deviceToken.setDeviceToken(token);
			deviceToken.setCreateTime(new Date());

			deviceTokenManager.save(deviceToken);
		}
		return root;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{app_version_id}")
	public @ResponseBody
	Root getAll(@PathVariable Long app_version_id) {
		root.setObject(deviceTokenManager.getAllByAppId(app_version_id));
		return root;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	Root delete(@PathVariable Long id) {
		deviceTokenManager.remove(id);
		return root;
	}
}
