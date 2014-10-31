package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.CodeOsStyle;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the FullTrialCode
 * table.
 */
public interface CodeOsStyleDao extends GenericDao<CodeOsStyle, Long> {

	boolean exists(CodeOsStyle codeOsStyle);
	
	CodeOsStyle exists(String text);

	List<CodeOsStyle> getAllByParentId(Long pid);
	
	/**
	 * 系统版本型号列表
	 * @param pageSize
	 * @return
	 */
	public Page codeOsStyleList(String pageSize);
}