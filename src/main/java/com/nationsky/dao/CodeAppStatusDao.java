package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.CodeAppStatus;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the FullTrialCode table.
 */
public interface CodeAppStatusDao extends GenericDao<CodeAppStatus, Long> {

	boolean exists(CodeAppStatus codeOs);
	
	public CodeAppStatus exists(String text);
	
	/**
	 * 应用状态码列表
	 * @param pageSize
	 * @return
	 */
	public Page codeAppStatusList(String pageSize);
}