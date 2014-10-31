package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.Praise;

/**
 * An interface that provides a data management interface to the Praise table.
 */
public interface PraiseDao  extends GenericDao<Praise, Long> {

	List<Praise> findPraiseList(String ideaId);

}
