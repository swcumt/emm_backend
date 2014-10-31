package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CodeAppStatusDao;
import com.nationsky.model.CodeAppStatus;
import com.nationsky.model.CodeOs;
import com.nationsky.service.CodeAppStatusManager;
import com.nationsky.vo.Page;

@Service("codeAppStatusManager")
@WebService(serviceName = "CodeAppStatusService", endpointInterface = "com.nationsky.service.CodeAppStatusManager")
public class CodeAppStatusManagerImpl extends GenericManagerImpl<CodeAppStatus, Long> implements CodeAppStatusManager {
	CodeAppStatusDao codeAppStatusDao;

	@Autowired
	public CodeAppStatusManagerImpl(CodeAppStatusDao codeAppStatusDao) {
		super(codeAppStatusDao);
		this.codeAppStatusDao = codeAppStatusDao;
	}
	
	public boolean exists(CodeAppStatus codeOs) {
		return codeAppStatusDao.exists(codeOs);
	}
	
	public CodeAppStatus exists(String text) {
		return codeAppStatusDao.exists(text);
	}

	@Override
	public Page codeAppStatusList(String pageSize) {
		return codeAppStatusDao.codeAppStatusList(pageSize);
	}
}