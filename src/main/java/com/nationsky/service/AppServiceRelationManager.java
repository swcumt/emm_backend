package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.AppServiceRelation;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.model.Service;

@WebService
public interface AppServiceRelationManager extends GenericManager<AppServiceRelation, Long> {
	
	/**
	 * 检测是否重复
	 * @param appVersionId 应用版本ID
	 * @param serviceId 服务ID
	 * @return
	 */
	boolean exists(Long appVersionId,Long serviceId);

	/**
	 * 获取应用和服务的关联信息
	 * @param appVersionId
	 * @param serviceId
	 * @return
	 */
	AppServiceRelation get(Long appVersionId, Long serviceId);
	
	/**
	 * 根据应用版本Id获取关联列表
	 * @param appVersionId 应用版本ID
	 * @return
	 */
	List<AppServiceRelation> getListByAppVersion(Long appVersionId);
	/**
	 * 保存用户配置
	 * @param appVersionId 应用版本信息
	 * @param serviceId 服务ID
	 * @param config 配置项结构体
	 */
	AppServiceRelation saveConfig(Long appVersionId, Long serviceId, String config);

	/**
	 * 为应用删除服务<br>
	 * 删除应用和服务的关联
	 * @param appVersionId 应用版本ID
	 * @param serviceId 服务ID
	 */
	void remove(Long appVersionId, Long serviceId);

	/**
	 * 获取应用所有服务
	 * @param appVersionId
	 * @return
	 */
	List<Service> getServiceListByApp(Long appVersionId);

	/**
	 * 获取使用服务的应用列表
	 * @param serviceId
	 * @return
	 */
	List<AppstoreEdition> getAppListByService(Long serviceId);
	
	

}