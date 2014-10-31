package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.AppServiceRelationDao;
import com.nationsky.model.AppServiceRelation;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.service.AppServiceRelationManager;

@Service("appServiceRelationManager")
@WebService(serviceName = "AppServiceRelationService", endpointInterface = "com.nationsky.service.AppServiceRelationManager")
public class AppServiceRelationManagerImpl extends GenericManagerImpl<AppServiceRelation, Long> implements AppServiceRelationManager {
	AppServiceRelationDao appServiceRelationDao;

	@Autowired
	public AppServiceRelationManagerImpl(AppServiceRelationDao appServiceRelationDao) {
		super(appServiceRelationDao);
		this.appServiceRelationDao = appServiceRelationDao;
	}

	public void remove(Long appVersionId, Long serviceId) {
		// TODO Auto-generated method stub
		appServiceRelationDao.remove(appVersionId, serviceId);
	}

	@Override
	public List<com.nationsky.model.Service> getServiceListByApp(Long appVersionId) {
		// TODO Auto-generated method stub
		return appServiceRelationDao.getServiceListByApp(appVersionId);
	}

	@Override
	public List<AppstoreEdition> getAppListByService(Long serviceId) {
		// TODO Auto-generated method stub
		return appServiceRelationDao.getAppListByService(serviceId);
	}

	@Override
	public AppServiceRelation saveConfig(Long appVersionId, Long serviceId, String config) {
		// TODO Auto-generated method stub
		AppServiceRelation appServiceRelation = appServiceRelationDao.get(appVersionId, serviceId);
		appServiceRelation.setConfig(config);
		appServiceRelation.setSetted(1);
		return appServiceRelationDao.save(appServiceRelation);
	}

	@Override
	public AppServiceRelation get(Long appVersionId, Long serviceId) {
		// TODO Auto-generated method stub
		return appServiceRelationDao.get(appVersionId, serviceId);
	}

	@Override
	public List<AppServiceRelation> getListByAppVersion(Long appVersionId) {
		// TODO Auto-generated method stub
		return appServiceRelationDao.getListByAppVersion(appVersionId);
	}

	@Override
	public boolean exists(Long appVersionId, Long serviceId) {
		// TODO Auto-generated method stub
		AppServiceRelation appServiceRelation = appServiceRelationDao.get(appVersionId, serviceId);
		if (appServiceRelation != null) {
			return true;
		}
		return false;
	}

}