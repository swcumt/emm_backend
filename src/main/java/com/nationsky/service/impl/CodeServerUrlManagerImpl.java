package com.nationsky.service.impl;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.CodeServerUrlDao;
import com.nationsky.model.CodeServerUrl;
import com.nationsky.service.CodeServerUrlManager;
import com.nationsky.webapp.util.Utils;

@Service("codeServerUrlManager")
@WebService(serviceName = "CodeServerUrlService", endpointInterface = "com.nationsky.service.CodeServerUrlManager")
public class CodeServerUrlManagerImpl extends GenericManagerImpl<CodeServerUrl, Long> implements CodeServerUrlManager {
	CodeServerUrlDao codeServerUrlDao;

	@Autowired
	public CodeServerUrlManagerImpl(CodeServerUrlDao codeServerUrlDao) {
		super(codeServerUrlDao);
		this.codeServerUrlDao = codeServerUrlDao;
	}
	
	@Override
	public CodeServerUrl getMdmDbServer() {
		// TODO Auto-generated method stub
		String mdmDbServerId = Utils.getPropertiesValue("MDM_SERVER_CONFIG_ID");
		return codeServerUrlDao.get(Long.valueOf(mdmDbServerId));
	}
	
	@Override
	public CodeServerUrl getSdkPackingServer() {
		// TODO Auto-generated method stub
		String sdkPackingServerId = Utils.getPropertiesValue("SDK_SERVER_CONFIG_ID");
		return codeServerUrlDao.get(Long.valueOf(sdkPackingServerId));
	}
	
	@Override
	public CodeServerUrl saveMdmDbServer(CodeServerUrl codeServerUrl) {
		// TODO Auto-generated method stub
		String mdmDbServerId = Utils.getPropertiesValue("MDM_SERVER_CONFIG_ID");
		codeServerUrl.setId(Long.valueOf(mdmDbServerId));
		return codeServerUrlDao.save(codeServerUrl);
	}

	@Override
	public CodeServerUrl saveSdkPackingServer(CodeServerUrl codeServerUrl) {
		// TODO Auto-generated method stub
		String sdkPackingServerId = Utils.getPropertiesValue("SDK_SERVER_CONFIG_ID");
		codeServerUrl.setId(Long.valueOf(sdkPackingServerId));
		return codeServerUrlDao.save(codeServerUrl);
	}
}