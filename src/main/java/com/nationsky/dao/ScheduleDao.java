package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.Schedule;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the Schedule table.
 */
public interface ScheduleDao extends GenericDao<Schedule, Long> {

	/**
	 * 根据服务项ID查询子服务项列表
	 * @param scheduleId
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	public List findScheduleList(String scheduleId);
    
    /**
     * 根据服务项ID和子服务项ID查询人员列表
     * @param schedule_id
     * @param schedule_list_id
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List findScheduleUser(String schedule_id,String schedule_list_id);
    
    /**
     * 服务类列表
     * @return
     */
    public Page findShceduleList(String pageSize); 
    
}