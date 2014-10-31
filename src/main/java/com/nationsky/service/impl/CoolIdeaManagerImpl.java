package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CoolIdeaDao;
import com.nationsky.model.CoolIdea;
import com.nationsky.service.CoolIdeaManager;

@Service("coolIdeaManager")
@WebService(serviceName = "CoolIdeaService", endpointInterface = "com.nationsky.service.CoolIdeaManager")
public class CoolIdeaManagerImpl extends GenericManagerImpl<CoolIdea, Long> implements CoolIdeaManager{

	CoolIdeaDao coolIdeaDao;
	
	@Autowired
	public CoolIdeaManagerImpl(CoolIdeaDao coolIdeaDao) {
		super(coolIdeaDao);
		this.coolIdeaDao = coolIdeaDao;
	}
	
	@Override
	public List<CoolIdea> getAllNewest() {
		return coolIdeaDao.getAllNewest();
	}

	@Override
	public List<CoolIdea> getAllHeatest() {
		return coolIdeaDao.getAllHeatest();
	}

	@Override
	public List<CoolIdea> getAllAdopted() {
		return coolIdeaDao.getAllAdopted();
	}

	@Override
	public List<CoolIdea> getAllByFlag(String flag) {
		return coolIdeaDao.getAllByFlag(flag);
	}

}
