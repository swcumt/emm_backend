package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.ScheduleListUser;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the ScheduleListUser table.
 */
public interface ScheduleListUserDao extends GenericDao<ScheduleListUser, Long> {

	/**
	 * 排班列表
	 * @return
	 */
	public Page findScheduleListUser(String pageSize);
}