package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.ScheduleUser;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the ScheduleUser table.
 */
public interface ScheduleUserDao extends GenericDao<ScheduleUser, Long> {

	/**
	 * 值班人员列表
	 * @return
	 */
	public Page findScheduleUser(String pageSize);
	
	/**
	 * 值班人员列表
	 * @return
	 */
	public List<ScheduleUser> findScheduleUsers(String flag,String userId);
}