package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.ScheduleList;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the ScheduleList table.
 */
public interface ScheduleListDao extends GenericDao<ScheduleList, Long> {
	
	public List<ScheduleList> findShceduleList(String scheduleId); 
	
	   /**
     * 自服务项列表
     * @return
     */
    public Page findScheduleList(String pageSize);
	
}