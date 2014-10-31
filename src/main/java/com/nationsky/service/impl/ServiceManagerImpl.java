package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.ServiceDao;
import com.nationsky.service.ServiceManager;
import com.nationsky.vo.Page;

@Service("serviceManager")
@WebService(serviceName = "serviceService", endpointInterface = "com.nationsky.service.ServiceManager")
public class ServiceManagerImpl extends GenericManagerImpl<com.nationsky.model.Service, Long> implements ServiceManager {
	ServiceDao serviceDao;

	@Autowired
	public ServiceManagerImpl(ServiceDao serviceDao) {
		super(serviceDao);
		this.serviceDao = serviceDao;
	}

	@Override
	public Page getServiceList(String pageSize) {
		// TODO Auto-generated method stub
		return serviceDao.getServiceList(pageSize);
	}
	
	
}