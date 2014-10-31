package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CodeModelDao;
import com.nationsky.model.CodeModel;
import com.nationsky.service.CodeModelManager;
import com.nationsky.vo.Page;

@Service("codeModelManager")
@WebService(serviceName = "CodeModelService", endpointInterface = "com.nationsky.service.CodeModelManager")
public class CodeModelManagerImpl extends GenericManagerImpl<CodeModel, Long> implements CodeModelManager {
	CodeModelDao codeModelDao;

	@Autowired
	public CodeModelManagerImpl(CodeModelDao codeOsVersionModelDao) {
		super(codeOsVersionModelDao);
		this.codeModelDao = codeOsVersionModelDao;
	}

	public boolean exists(CodeModel codeOs) {
		return codeModelDao.exists(codeOs);
	}

	public CodeModel exists(String text) {
		return codeModelDao.exists(text);
	}

	@Override
	public Page codeOsList(String pageSize) {
		return codeModelDao.codeOsList(pageSize);
	}
}