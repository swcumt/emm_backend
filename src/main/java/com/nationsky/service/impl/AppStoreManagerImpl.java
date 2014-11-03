package com.nationsky.service.impl;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.AppStoreDao;
import com.nationsky.model.AppStore;
import com.nationsky.service.AppStoreManager;
import com.nationsky.vo.AppStoreVO;
import com.nationsky.vo.Page;

@Service("appStoreManager")
@WebService(serviceName = "AppStoreService", endpointInterface = "com.nationsky.service.AppStoreManager")
public class AppStoreManagerImpl extends GenericManagerImpl<AppStore, Long> implements AppStoreManager {
	AppStoreDao appStoreDao;

	@Autowired
	public AppStoreManagerImpl(AppStoreDao appStoreDao) {
		super(appStoreDao);
		this.appStoreDao = appStoreDao;
	}

	@SuppressWarnings("unchecked")
	public List<AppStoreVO> appList(Long osId, String modelType) {
		List<AppStoreVO> appList = appStoreDao.appList(osId, modelType);
		return appList;
	}

	@SuppressWarnings("unchecked")
	public List<AppStoreVO> appList(String modelType) {
		List<AppStoreVO> appList = appStoreDao.appList(modelType);
		return appList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public AppStoreVO appInfo(String appId) {
		List appList = appStoreDao.appInfo(appId);
		AppStoreVO appStore = null;
		if (appList != null && appList.size() > 0) {
			Map appMap = (Map) appList.get(0);
			appStore = new AppStoreVO();
			appStore.setAppId(appMap.get("id") == null ? "" : appMap.get("id").toString());
			appStore.setAppSize(appMap.get("appSize") == null ? "未知" : appMap.get("appSize").toString());
			appStore.setCreateTime(appMap.get("createTime") == null ? "" : appMap.get("createTime").toString());
			appStore.setDescriptionText(appMap.get("description") == null ? "" : appMap.get("description").toString());
			appStore.setFullTrialText(appMap.get("text") == null ? "" : appMap.get("text").toString());
			appStore.setIcon(appMap.get("icon") == null ? "" : appMap.get("icon").toString());
			appStore.setIconInfo(appMap.get("iconInfo") == null ? "" : appMap.get("iconInfo").toString());
			appStore.setNAME(appMap.get("name") == null ? "" : appMap.get("name").toString());
			appStore.setPlistUrl(appMap.get("plistUrl") == null ? "" : appMap.get("plistUrl").toString());
			appStore.setSchemesUrl(appMap.get("schemesUrl") == null ? "" : appMap.get("schemesUrl").toString());
			appStore.setVersion(appMap.get("versions") == null ? "" : appMap.get("versions").toString());
			appStore.setIpaUrl(appMap.get("ipaUrl") == null ? "" : appMap.get("ipaUrl").toString());
			appStore.setService_type(appMap.get("service_type") == null ? "" : appMap.get("service_type").toString());
		}
		return appStore;
	}

	@Override
	public Page getAppStore(String pageSize) {
		return appStoreDao.getAppStore(pageSize);
	}

	@Override
	public Page getAppStoreByUser(String pageSize, Long userId,Long osId) {
		return appStoreDao.getAppStoreByUser(pageSize, userId,osId);
	}

	@Override
	public Page getAppStoreByProject(String pageSize, Long projectId,Long osId) {
		return appStoreDao.getAppStoreByProject(pageSize, projectId,osId);
	}
}