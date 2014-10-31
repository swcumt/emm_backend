package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.ScheduleDao;
import com.nationsky.model.Schedule;
import com.nationsky.service.ScheduleManager;
import com.nationsky.vo.Page;

@Service("scheduleManager")
@WebService(serviceName = "ScheduleService", endpointInterface = "com.nationsky.service.ScheduleManager")
public class ScheduleManagerImpl extends GenericManagerImpl<Schedule, Long> implements ScheduleManager {
    ScheduleDao scheduleDao;

    @Autowired
    public ScheduleManagerImpl(ScheduleDao scheduleDao) {
        super(scheduleDao);
        this.scheduleDao = scheduleDao;
    }

	@SuppressWarnings("rawtypes")
	public List findScheduleList(String scheduleId) {
		return scheduleDao.findScheduleList(scheduleId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findScheduleUser(String schedule_id, String schedule_list_id) {
		return scheduleDao.findScheduleUser(schedule_id, schedule_list_id);
	}

	@Override
	public Page findShceduleList(String pageSize) {
		return scheduleDao.findShceduleList(pageSize);
	}
}