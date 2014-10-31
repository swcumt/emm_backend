package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.AppstoreEdition;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the AppstoreEdition table.
 */
public interface AppstoreEditionDao extends GenericDao<AppstoreEdition, Long> {

	/**
	 * 版本列表
	 * @return
	 */
	public Page getAppstoreEdition(String appid,String pageSize);
	
	public AppstoreEdition getAppStoreEdition(String version,String appId);
	public AppstoreEdition checkVersion(String version,String appId);
	AppstoreEdition getMaxVersion(String appId);
	/**
	 * 历史版本
	 * @param appId
	 * @return
	 */
	public List<AppstoreEdition> historyApp(String appId);
}