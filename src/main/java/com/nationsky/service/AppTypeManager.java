package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.AppType;
import com.nationsky.vo.Page;

@WebService
public interface AppTypeManager extends GenericManager<AppType, String> {
    
	/**
	 * 应用分类
	 * @return
	 */
	public Page findAppType(String pageSize);
}