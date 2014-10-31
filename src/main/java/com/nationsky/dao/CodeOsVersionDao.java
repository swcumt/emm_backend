package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.CodeOsVersion;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the FullTrialCode
 * table.
 */
public interface CodeOsVersionDao extends GenericDao<CodeOsVersion, Long> {
	boolean exists(CodeOsVersion codeOs);
	
	public CodeOsVersion exists(String text);

	List<CodeOsVersion> getAllByParentId(Long pid);

	/**
	 * 系统版本型号列表
	 * 
	 * @param pageSize
	 * @return
	 */
	public Page codeOsList(String pageSize);
}