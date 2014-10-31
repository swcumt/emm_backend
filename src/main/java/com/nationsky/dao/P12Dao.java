package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.P12;

/**
 * An interface that provides a data management interface to the Version table.
 */
public interface P12Dao extends GenericDao<P12, Long> {
	List<P12> getAllByAppId(Long appVersionId);
}