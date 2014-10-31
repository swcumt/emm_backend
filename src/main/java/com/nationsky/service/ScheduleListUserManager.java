package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.ScheduleListUser;
import com.nationsky.vo.Page;

@WebService
public interface ScheduleListUserManager extends GenericManager<ScheduleListUser, Long> {
    
	/**
	 * 排班列表
	 * @return
	 */
	public Page findScheduleListUser(String pageSize);
}