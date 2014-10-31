package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.ScheduleUserDao;
import com.nationsky.model.ScheduleUser;
import com.nationsky.service.ScheduleUserManager;
import com.nationsky.vo.Page;

@Service("scheduleUserManager")
@WebService(serviceName = "ScheduleUserService", endpointInterface = "com.nationsky.service.ScheduleUserManager")
public class ScheduleUserManagerImpl extends GenericManagerImpl<ScheduleUser, Long> implements ScheduleUserManager {
    ScheduleUserDao scheduleUserDao;

    @Autowired
    public ScheduleUserManagerImpl(ScheduleUserDao scheduleUserDao) {
        super(scheduleUserDao);
        this.scheduleUserDao = scheduleUserDao;
    }

	@Override
	public Page findScheduleUser(String pageSize) {
		return scheduleUserDao.findScheduleUser(pageSize);
	}

	@Override
	public List<ScheduleUser> findScheduleUsers(String flag,String userId) {
		return scheduleUserDao.findScheduleUsers(flag,userId);
	}
}