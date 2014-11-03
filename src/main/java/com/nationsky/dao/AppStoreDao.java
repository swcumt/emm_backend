package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.AppStore;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the AppStore table.
 */
public interface AppStoreDao extends GenericDao<AppStore, Long> {

	@SuppressWarnings("rawtypes")
	public List appList(Long osId, String modelType);

	@SuppressWarnings("rawtypes")
	public List appList(String modelType);

	@SuppressWarnings("rawtypes")
	public List appInfo(String appId);

	/**
	 * 应用列表
	 * @return
	 */
	public Page getAppStore(String pageSize);

	/**
	 * 应用列表
	 * @param osId 
	 * @return
	 */
	public Page getAppStoreByProject(String pageSize, Long projectId, Long osId);

	/**
	 * 应用列表
	 * @param osId 
	 * @return
	 */
	public Page getAppStoreByUser(String pageSize, Long userId, Long osId);
}