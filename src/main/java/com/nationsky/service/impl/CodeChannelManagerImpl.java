package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CodeChannelDao;
import com.nationsky.model.CodeChannel;
import com.nationsky.model.CodeOs;
import com.nationsky.service.CodeChannelManager;
import com.nationsky.vo.Page;

@Service("codeChannelManager")
@WebService(serviceName = "CodeChannelService", endpointInterface = "com.nationsky.service.CodeChannelManager")
public class CodeChannelManagerImpl extends GenericManagerImpl<CodeChannel, Long> implements CodeChannelManager {
	CodeChannelDao codeChannelDao;

	@Autowired
	public CodeChannelManagerImpl(CodeChannelDao codeChannelDao) {
		super(codeChannelDao);
		this.codeChannelDao = codeChannelDao;
	}

	public boolean exists(CodeChannel codeOs) {
		return codeChannelDao.exists(codeOs);
	}
	
	public CodeChannel exists(String text) {
		return codeChannelDao.exists(text);
	}

	@Override
	public Page codeChannelList(String pageSize) {
		return codeChannelDao.codeChannelList(pageSize);
	}
}