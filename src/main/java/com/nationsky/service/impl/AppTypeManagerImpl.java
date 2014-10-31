package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.AppTypeDao;
import com.nationsky.model.AppType;
import com.nationsky.service.AppTypeManager;
import com.nationsky.vo.Page;

@Service("appTypeManager")
@WebService(serviceName = "AppTypeService", endpointInterface = "com.nationsky.service.AppTypeManager")
public class AppTypeManagerImpl extends GenericManagerImpl<AppType, String> implements AppTypeManager {
    AppTypeDao appTypeDao;

    @Autowired
    public AppTypeManagerImpl(AppTypeDao appTypeDao) {
        super(appTypeDao);
        this.appTypeDao = appTypeDao;
    }

	@Override
	public Page findAppType(String pageSize) {
		return appTypeDao.findAppType(pageSize);
	}

}