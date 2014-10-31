package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.AppProject;
import com.nationsky.vo.Page;

@WebService
public interface AppProjectManager extends GenericManager<AppProject, Long> {
	boolean exists(AppProject appProject,Long id);

	Page getList(String pageSize,Long id);

}