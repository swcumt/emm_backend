package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.FrontGroupRelationDao;
import com.nationsky.model.FrontGroupRelation;
import com.nationsky.service.FrontGroupRelationManager;

@Service("frontGroupRelationManager")
@WebService(serviceName = "FrontGroupRelationService", endpointInterface = "com.nationsky.service.FrontGroupRelationManager")
public class FrontGroupRelationManagerImpl extends GenericManagerImpl<FrontGroupRelation, Long> implements FrontGroupRelationManager {

	FrontGroupRelationDao frontGroupRelationDao;
	@Autowired
	public FrontGroupRelationManagerImpl(FrontGroupRelationDao frontGroupRelationDao) {
		super(frontGroupRelationDao);
		this.frontGroupRelationDao = frontGroupRelationDao;
	}
	@Override
	public boolean delete(String groupId, String userId) {
		return frontGroupRelationDao.delete(groupId, userId);		
	}
	@Override
	public FrontGroupRelation find(Long groupId, String userId) {
		return frontGroupRelationDao.find(groupId, userId);
	}
	
}
