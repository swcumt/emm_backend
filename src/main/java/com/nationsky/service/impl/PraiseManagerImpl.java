package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.PraiseDao;
import com.nationsky.model.Praise;
import com.nationsky.service.PraiseManager;

@Service("praiseManager")
@WebService(serviceName = "PraiseService", endpointInterface = "com.nationsky.service.PraiseManager")
public class PraiseManagerImpl extends GenericManagerImpl<Praise, Long> implements PraiseManager{

	private PraiseDao praiseDao;

	@Autowired
	public PraiseManagerImpl(PraiseDao praiseDao) {
		super(praiseDao);
		this.praiseDao = praiseDao;
	}

	@Override
	public List<Praise> findPraiseList(String ideaId) {
		return praiseDao.findPraiseList(ideaId);
	}
	
	
}
