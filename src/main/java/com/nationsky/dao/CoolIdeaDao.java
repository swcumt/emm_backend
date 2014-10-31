package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.CoolIdea;

/**
 * An interface that provides a data management interface to the CoolIdea table.
 */
public interface CoolIdeaDao  extends GenericDao<CoolIdea, Long>{

	List<CoolIdea> getAllNewest();

	List<CoolIdea> getAllHeatest();

	List<CoolIdea> getAllAdopted();

	List<CoolIdea> getAllByFlag(String flag);

}
