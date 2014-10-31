package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.FrontGroupRelation;

/**
 * An interface that provides a data management interface to the FrontGroupRelation table.
 */
public interface FrontGroupRelationDao extends GenericDao<FrontGroupRelation, Long>{

	boolean delete(String groupId, String userId);

	FrontGroupRelation find(Long groupId, String userId);

}
