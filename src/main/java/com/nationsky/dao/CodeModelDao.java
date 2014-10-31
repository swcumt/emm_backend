package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.CodeModel;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the FullTrialCode
 * table.
 */
public interface CodeModelDao extends GenericDao<CodeModel, Long> {
	boolean exists(CodeModel codeOs);
	
	public CodeModel exists(String text);

	/**
	 * 系统版本型号列表
	 * 
	 * @param pageSize
	 * @return
	 */
	public Page codeOsList(String pageSize);
}