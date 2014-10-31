package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.DeviceToken;

/**
 * An interface that provides a data management interface to the Version table.
 */
public interface DeviceTokenDao extends GenericDao<DeviceToken, Long> {
	List<DeviceToken> getAllByAppId(Long app_version_id);

	List<String> getTokenByAppId(Long app_version_id);
}