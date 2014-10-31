package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.AppstoreEdition;
import com.nationsky.vo.Page;

@WebService
public interface AppstoreEditionManager extends GenericManager<AppstoreEdition, Long> {
    
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