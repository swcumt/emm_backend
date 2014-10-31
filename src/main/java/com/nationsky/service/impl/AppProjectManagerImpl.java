package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.AppProjectDao;
import com.nationsky.model.AppProject;
import com.nationsky.service.AppProjectManager;
import com.nationsky.vo.Page;

@Service("appProjectManager")
@WebService(serviceName = "AppProjectService", endpointInterface = "com.nationsky.service.AppProjectManager")
public class AppProjectManagerImpl extends GenericManagerImpl<AppProject, Long> implements AppProjectManager {
	AppProjectDao appProjectDao;

	@Autowired
	public AppProjectManagerImpl(AppProjectDao appProjectDao) {
		super(appProjectDao);
		this.appProjectDao = appProjectDao;
	}
	
	public boolean exists(AppProject appProject,Long id) {
		return appProjectDao.exists(appProject,id);
	}


	@Override
	public Page getList(String pageSize,Long id) {
		return appProjectDao.getList(pageSize,id);
	}
}