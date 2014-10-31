package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.P12Dao;
import com.nationsky.model.P12;
import com.nationsky.service.P12Manager;

@Service("p12Manager")
@WebService(serviceName = "P12Service", endpointInterface = "com.nationsky.service.P12Manager")
public class P12ManagerImpl extends GenericManagerImpl<P12, Long> implements P12Manager {
	P12Dao p12Dao;

	@Autowired
	public P12ManagerImpl(P12Dao p12Dao) {
		super(p12Dao);
		this.p12Dao = p12Dao;
	}

	@Override
	public List<P12> getAllByAppId(Long appVersionId) {
		// TODO Auto-generated method stub
		return p12Dao.getAllByAppId(appVersionId);
	}
}