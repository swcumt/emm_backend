package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.AppProject;
import com.nationsky.vo.Page;

public interface AppProjectDao extends GenericDao<AppProject, Long> {

	boolean exists(AppProject appProject,Long id);

	public Page getList(String pageSize,Long id);
	
	
}