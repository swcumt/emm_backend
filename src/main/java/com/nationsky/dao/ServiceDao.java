package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.Service;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the Version table.
 */
public interface ServiceDao extends GenericDao<Service, Long> {

	/**
	 * 服务列表
	 * @param pageSize
	 * @return
	 */
	public Page getServiceList(String pageSize);
}