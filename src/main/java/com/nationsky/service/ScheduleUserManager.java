package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.ScheduleUser;
import com.nationsky.vo.Page;

@WebService
public interface ScheduleUserManager extends GenericManager<ScheduleUser, Long> {
    
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