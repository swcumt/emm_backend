package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.CodeOs;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the FullTrialCode
 * table.
 */
public interface CodeOsDao extends GenericDao<CodeOs, Long> {

	boolean exists(CodeOs codeOs);
	
	CodeOs exists(String text);

	List<CodeOs> getAllByParentId(Long pid);
	
	/**
	 * 系统版本型号列表
	 * @param pageSize
	 * @return
	 */
	public Page codeOsList(String pageSize);
}