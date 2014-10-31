package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.AppType;
import com.nationsky.vo.Page;
/**
 * An interface that provides a data management interface to the Users table.
 */
public interface AppTypeDao extends GenericDao<AppType, String> {

	/**
	 * 应用分类
	 * @return
	 */
	public Page findAppType(String pageSize);
}