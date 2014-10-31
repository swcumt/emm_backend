package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.FrontUserGroupDao;
import com.nationsky.model.FrontUserGroup;
import com.nationsky.service.FrontUserGroupManager;

@Service("frontUserGroupManager")
@WebService(serviceName = "FrontUserGroupService", endpointInterface = "com.nationsky.service.FrontUserGroupManager")
public class FrontUserGroupManagerImpl extends GenericManagerImpl<FrontUserGroup, Long> implements FrontUserGroupManager {

	FrontUserGroupDao frontUserGroupDao;
	@Autowired
	public FrontUserGroupManagerImpl(FrontUserGroupDao frontUserGroupDao) {
		super(frontUserGroupDao);
		this.frontUserGroupDao = frontUserGroupDao;
	}
	
	
}
