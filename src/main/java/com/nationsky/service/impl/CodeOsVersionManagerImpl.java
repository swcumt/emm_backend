package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CodeOsVersionDao;
import com.nationsky.model.CodeDeveloper;
import com.nationsky.model.CodeOsVersion;
import com.nationsky.service.CodeOsVersionManager;
import com.nationsky.vo.Page;

@Service("codeOsVersionManager")
@WebService(serviceName = "CodeOsVersionService", endpointInterface = "com.nationsky.service.CodeOsVersionManager")
public class CodeOsVersionManagerImpl extends
		GenericManagerImpl<CodeOsVersion, Long> implements CodeOsVersionManager {
	CodeOsVersionDao codeOsVersionDao;

	@Autowired
	public CodeOsVersionManagerImpl(CodeOsVersionDao codeOsVersionDao) {
		super(codeOsVersionDao);
		this.codeOsVersionDao = codeOsVersionDao;
	}

	public boolean exists(CodeOsVersion codeOs) {
		return codeOsVersionDao.exists(codeOs);
	}
	public CodeOsVersion exists(String text) {
		return codeOsVersionDao.exists(text);
	}

	@Override
	public List<CodeOsVersion> getAllByParentId(Long pid) {
		// TODO Auto-generated method stub
		return codeOsVersionDao.getAllByParentId(pid);
	}

	@Override
	public Page codeOsList(String pageSize) {
		return codeOsVersionDao.codeOsList(pageSize);
	}
}