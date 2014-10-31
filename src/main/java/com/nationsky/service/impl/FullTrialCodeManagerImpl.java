package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.FullTrialCodeDao;
import com.nationsky.model.FullTrialCode;
import com.nationsky.service.FullTrialCodeManager;
import com.nationsky.vo.Page;

@Service("fullTrialCodeManager")
@WebService(serviceName = "FullTrialCodeService", endpointInterface = "com.nationsky.service.FullTrialCodeManager")
public class FullTrialCodeManagerImpl extends GenericManagerImpl<FullTrialCode, Long> implements FullTrialCodeManager {
    FullTrialCodeDao fullTrialCodeDao;

    @Autowired
    public FullTrialCodeManagerImpl(FullTrialCodeDao fullTrialCodeDao) {
        super(fullTrialCodeDao);
        this.fullTrialCodeDao = fullTrialCodeDao;
    }

	public FullTrialCode findFullTrialCode(String text) {
		return fullTrialCodeDao.findFullTrialCode(text);
	}

	public boolean exists(FullTrialCode fullTrialCode) {
		return fullTrialCodeDao.exists(fullTrialCode);
	}

	@Override
	public Page getfindFullTrialCode(String pageSize) {
		return fullTrialCodeDao.getfindFullTrialCode(pageSize);
	}
}