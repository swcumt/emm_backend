package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.FrontUser;

/**
 * An interface that provides a data management interface to the FrontUser table.
 */
public interface FrontUserDao extends GenericDao<FrontUser, String>{

	FrontUser findUsers(String username, String password);

	List<FrontUser> getGroupUsers(String groupId);

	List<FrontUser> findByLoginName(String loginName);

}
