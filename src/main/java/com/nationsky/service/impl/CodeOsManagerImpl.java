package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CodeOsDao;
import com.nationsky.model.CodeOs;
import com.nationsky.service.CodeOsManager;
import com.nationsky.vo.Page;

@Service("codeOsManager")
@WebService(serviceName = "CodeOsService", endpointInterface = "com.nationsky.service.CodeOsManager")
public class CodeOsManagerImpl extends GenericManagerImpl<CodeOs, Long> implements CodeOsManager {
	CodeOsDao codeOsDao;

	@Autowired
	public CodeOsManagerImpl(CodeOsDao codeOsDao) {
		super(codeOsDao);
		this.codeOsDao = codeOsDao;
	}
	
	public boolean exists(CodeOs codeOs) {
		return codeOsDao.exists(codeOs);
	}

	public CodeOs exists(String text) {
		return codeOsDao.exists(text);
	}

	@Override
	public List<CodeOs> getAllByParentId(Long pid) {
		// TODO Auto-generated method stub
		return codeOsDao.getAllByParentId(pid);
	}

	@Override
	public Page codeOsList(String pageSize) {
		return codeOsDao.codeOsList(pageSize);
	}
}