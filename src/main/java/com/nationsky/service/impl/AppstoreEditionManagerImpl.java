package com.nationsky.service.impl;

import java.util.List;

import com.nationsky.dao.AppstoreEditionDao;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.service.AppstoreEditionManager;
import com.nationsky.vo.Page;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service("appstoreEditionManager")
@WebService(serviceName = "AppstoreEditionService", endpointInterface = "com.nationsky.service.AppstoreEditionManager")
public class AppstoreEditionManagerImpl extends GenericManagerImpl<AppstoreEdition, Long> implements AppstoreEditionManager {
	AppstoreEditionDao appstoreEditionDao;

	@Autowired
	public AppstoreEditionManagerImpl(AppstoreEditionDao appstoreEditionDao) {
		super(appstoreEditionDao);
		this.appstoreEditionDao = appstoreEditionDao;
	}

	@Override
	public Page getAppstoreEdition(String appid, String pageSize) {
		return appstoreEditionDao.getAppstoreEdition(appid, pageSize);
	}

	@Override
	public AppstoreEdition getAppStoreEdition(String version, String appId) {
		return appstoreEditionDao.getAppStoreEdition(version, appId);
	}

	public AppstoreEdition checkVersion(String version, String appId) {
		return appstoreEditionDao.checkVersion(version, appId);
	}

	@Override
	public List<AppstoreEdition> historyApp(String appId) {
		return appstoreEditionDao.historyApp(appId);
	}

	@Override
	public AppstoreEdition getMaxVersion(String appId) {
		// TODO Auto-generated method stub
		return appstoreEditionDao.getMaxVersion(appId);
	}
}