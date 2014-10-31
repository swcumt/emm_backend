package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CodeOsStyleDao;
import com.nationsky.model.CodeOsStyle;
import com.nationsky.service.CodeOsStyleManager;
import com.nationsky.vo.Page;

@Service("codeOsStyleManager")
@WebService(serviceName = "CodeOsStyleService", endpointInterface = "com.nationsky.service.CodeOsStyleManager")
public class CodeOsStyleManagerImpl extends GenericManagerImpl<CodeOsStyle, Long> implements CodeOsStyleManager {
	CodeOsStyleDao codeOsStyleDao;

	@Autowired
	public CodeOsStyleManagerImpl(CodeOsStyleDao codeOsDao) {
		super(codeOsDao);
		this.codeOsStyleDao = codeOsDao;
	}

	public boolean exists(CodeOsStyle codeOs) {
		return codeOsStyleDao.exists(codeOs);
	}

	public CodeOsStyle exists(String text) {
		return codeOsStyleDao.exists(text);
	}

	@Override
	public List<CodeOsStyle> getAllByParentId(Long pid) {
		// TODO Auto-generated method stub
		return codeOsStyleDao.getAllByParentId(pid);
	}

	@Override
	public Page codeOsStyleList(String pageSize) {
		return codeOsStyleDao.codeOsStyleList(pageSize);
	}
}