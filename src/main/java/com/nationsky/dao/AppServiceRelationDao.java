package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.AppServiceRelation;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.model.Service;

/**
 * An interface that provides a data management interface to the Version table.
 */
public interface AppServiceRelationDao extends GenericDao<AppServiceRelation, Long> {
	
	/**
	 * 根据应用版本Id获取关联列表
	 * @param appVersionId 版本ID
	 * @return
	 */
	List<AppServiceRelation> getListByAppVersion(Long appVersionId);
	/**
	 * 获取应用和服务的关联信息
	 * @param appVersionId
	 * @param serviceId
	 * @return
	 */
	AppServiceRelation get(Long appVersionId, Long serviceId);

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