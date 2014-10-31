package com.nationsky.api.v1;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.AppServiceRelation;
import com.nationsky.service.AppServiceRelationManager;
import com.nationsky.service.ServiceManager;
import com.nationsky.utils.FileUtils;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;

@Controller
@RequestMapping("/v1/appServiceRelation")
@Scope("prototype")
public class AppServiceRelationController extends BaseFormController {
	@Autowired
	private AppServiceRelationManager appServiceRelationManager = null;

	@Autowired
	private ServiceManager serviceManager;

	@Autowired
	private Root root;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Root save(AppServiceRelation appServiceRelation) {
		if (appServiceRelation == null) {
			root.setError("参数不符!");
		} else {
			if (!appServiceRelationManager.exists(appServiceRelation.getAppstoreEdition().getId(), appServiceRelation.getService().getId())) {
				String SERVICE_CONFIG_FOLDER_PATH = Utils.getPropertiesValue("SERVICE_CONFIG_FOLDER_PATH");
				SERVICE_CONFIG_FOLDER_PATH += appServiceRelation.getService().getId() + ".json";
				String json = FileUtils.readFileByLines(SERVICE_CONFIG_FOLDER_PATH);
				appServiceRelation.setConfig(json);
				appServiceRelation.setSetted(0);
				appServiceRelation.setCreateTime(new Date());
				appServiceRelation = appServiceRelationManager.save(appServiceRelation);
				if (appServiceRelation == null) {
					root.setError("保存失败");
				}
			}else{
				root.setMessage(2, "服务已存在");
			}
		}
		return root;
	}

	/**
	 * 获取使用某服务的应用列表
	 * @param serviceId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/appversion/{serviceId}")
	public @ResponseBody Root getAppListByService(@PathVariable Long serviceId) {
		root.setObject(appServiceRelationManager.getAppListByService(serviceId));
		return root;
	}

	/**
	 * 获取某应用使用的所有服务
	 * @param appVersionId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/relation/{appVersionId}")
	public @ResponseBody Root getServiceListByApp(@PathVariable Long appVersionId) {
		root.setObject(appServiceRelationManager.getListByAppVersion(appVersionId));
		//root.setObject(appServiceRelationManager.getServiceListByApp(appVersionId));
		return root;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody Root getById(@PathVariable Long id) {
		root.setObject(appServiceRelationManager.get(id));
		return root;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{appVersionId}/{serviceId}")
	public @ResponseBody Root delete(@PathVariable Long appVersionId, @PathVariable Long serviceId) {
		appServiceRelationManager.remove(appVersionId, serviceId);
		return root;
	}

}
