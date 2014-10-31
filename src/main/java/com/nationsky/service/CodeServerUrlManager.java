package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CodeServerUrl;

@WebService
public interface CodeServerUrlManager extends GenericManager<CodeServerUrl, Long> {
	CodeServerUrl getMdmDbServer();
	CodeServerUrl getSdkPackingServer();
	CodeServerUrl saveMdmDbServer(CodeServerUrl codeServerUrl);
	CodeServerUrl saveSdkPackingServer(CodeServerUrl codeServerUrl);
	
}