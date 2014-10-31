package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CodeDeveloperDao;
import com.nationsky.model.CodeChannel;
import com.nationsky.model.CodeDeveloper;
import com.nationsky.service.CodeDeveloperManager;

@Service("codeDeveloperManager")
@WebService(serviceName = "CodeDeveloperService", endpointInterface = "com.nationsky.service.CodeDeveloperManager")
public class CodeDeveloperManagerImpl extends GenericManagerImpl<CodeDeveloper, Long> implements CodeDeveloperManager {
	CodeDeveloperDao codeDeveloperDao;

	@Autowired
	public CodeDeveloperManagerImpl(CodeDeveloperDao codeDeveloperDao) {
		super(codeDeveloperDao);
		this.codeDeveloperDao = codeDeveloperDao;
	}

	public boolean exists(CodeDeveloper codeOs) {
		return codeDeveloperDao.exists(codeOs);
	}
	public CodeDeveloper exists(String text) {
		return codeDeveloperDao.exists(text);
	}
}