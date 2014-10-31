package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CodeAppStatus;
import com.nationsky.vo.Page;

@WebService
public interface CodeAppStatusManager extends GenericManager<CodeAppStatus, Long> {
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	boolean exists(CodeAppStatus codeOs);
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	public CodeAppStatus exists(String text);
	
	/**
	 * 应用状态码列表
	 * @param pageSize
	 * @return
	 */
	public Page codeAppStatusList(String pageSize);
}