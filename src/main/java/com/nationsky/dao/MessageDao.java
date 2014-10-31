package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.Message;

/**
 * An interface that provides a data management interface to the Version table.
 */
public interface MessageDao extends GenericDao<Message, Long> {
	List<Message> getAllByAppId(Long app_version_id);
}