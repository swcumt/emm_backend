package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.CodeDeveloper;

/**
 * An interface that provides a data management interface to the FullTrialCode table.
 */
public interface CodeDeveloperDao extends GenericDao<CodeDeveloper, Long> {
	boolean exists(CodeDeveloper codeOs);
	
	public CodeDeveloper exists(String text);
}