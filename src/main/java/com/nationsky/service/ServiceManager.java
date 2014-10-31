package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.Service;
import com.nationsky.vo.Page;

@WebService
public interface ServiceManager extends GenericManager<Service, Long> {
	
	/**
	 * 服务列表
	 * @param pageSize
	 * @return
	 */
	public Page getServiceList(String pageSize);
}