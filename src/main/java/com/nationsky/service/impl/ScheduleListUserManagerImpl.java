package com.nationsky.service.impl;

import com.nationsky.dao.ScheduleListUserDao;
import com.nationsky.model.ScheduleListUser;
import com.nationsky.service.ScheduleListUserManager;
import com.nationsky.vo.Page;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service("scheduleListUserManager")
@WebService(serviceName = "ScheduleListUserService", endpointInterface = "com.nationsky.service.ScheduleListUserManager")
public class ScheduleListUserManagerImpl extends GenericManagerImpl<ScheduleListUser, Long> implements ScheduleListUserManager {
    ScheduleListUserDao scheduleListUserDao;

    @Autowired
    public ScheduleListUserManagerImpl(ScheduleListUserDao scheduleListUserDao) {
        super(scheduleListUserDao);
        this.scheduleListUserDao = scheduleListUserDao;
    }

	@Override
	public Page findScheduleListUser(String pageSize) {
		return scheduleListUserDao.findScheduleListUser(pageSize);
	}
}