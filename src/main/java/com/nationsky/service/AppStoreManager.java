package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.AppStore;
import com.nationsky.vo.AppStoreVO;
import com.nationsky.vo.Page;

@WebService
public interface AppStoreManager extends GenericManager<AppStore, Long> {
    
	public List<AppStoreVO> appList(Long osId,String modelType);
	public List<AppStoreVO> appList(String modelType);
	
	public AppStoreVO appInfo(String appId);
	
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
	public Page getAppStoreByProject(String pageSize,Long projectId, Long osId);
	
	/**
	 * 应用列表
	 * @param osId 
	 * @return
	 */
	public Page getAppStoreByUser(String pageSize,Long userId, Long osId);
}