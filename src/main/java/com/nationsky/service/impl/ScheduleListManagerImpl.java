package com.nationsky.service.impl;

import com.nationsky.dao.ScheduleListDao;
import com.nationsky.model.ScheduleList;
import com.nationsky.service.ScheduleListManager;
import com.nationsky.vo.Page;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.jws.WebService;

@Service("scheduleListManager")
@WebService(serviceName = "ScheduleListService", endpointInterface = "com.nationsky.service.ScheduleListManager")
public class ScheduleListManagerImpl extends GenericManagerImpl<ScheduleList, Long> implements ScheduleListManager {
    ScheduleListDao scheduleListDao;

    @Autowired
    public ScheduleListManagerImpl(ScheduleListDao scheduleListDao) {
        super(scheduleListDao);
        this.scheduleListDao = scheduleListDao;
    }

	@Override
	public List<ScheduleList> findShceduleList(String scheduleId) {
		return scheduleListDao.findShceduleList(scheduleId);
	}

	@Override
	public Page findScheduleList(String pageSize) {
		return scheduleListDao.findScheduleList(pageSize);
	}

}