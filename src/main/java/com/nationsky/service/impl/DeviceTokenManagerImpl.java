package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.DeviceTokenDao;
import com.nationsky.model.DeviceToken;
import com.nationsky.service.DeviceTokenManager;

@Service("deviceTokenManager")
@WebService(serviceName = "DeviceTokenService", endpointInterface = "com.nationsky.service.DeviceTokenManager")
public class DeviceTokenManagerImpl extends GenericManagerImpl<DeviceToken, Long> implements DeviceTokenManager {
	DeviceTokenDao deviceTokenDao;

	@Autowired
	public DeviceTokenManagerImpl(DeviceTokenDao deviceTokenDao) {
		super(deviceTokenDao);
		this.deviceTokenDao = deviceTokenDao;
	}
	
	@Override
	public List<DeviceToken> getAllByAppId(Long app_version_id) {
		// TODO Auto-generated method stub
		return deviceTokenDao.getAllByAppId(app_version_id);
	}

	@Override
	public List<String> getTokenByAppId(Long app_version_id) {
		// TODO Auto-generated method stub
		return deviceTokenDao.getTokenByAppId(app_version_id);
	}
}