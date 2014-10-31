package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.ScheduleList;
import com.nationsky.vo.Page;

@WebService
public interface ScheduleListManager extends GenericManager<ScheduleList, Long> {
	/**
	 * 根据主服务项查询自服务项
	 * @param scheduleId
	 * @return
	 */
    public List<ScheduleList> findShceduleList(String scheduleId); 
    
    /**
     * 自服务项列表
     * @return
     */
    public Page findScheduleList(String pageSize);
    
}