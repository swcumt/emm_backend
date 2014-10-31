package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.FullTrialCode;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the FullTrialCode table.
 */
public interface FullTrialCodeDao extends GenericDao<FullTrialCode, Long> {

	public FullTrialCode findFullTrialCode(String text);

	boolean exists(FullTrialCode fullTrialCode);
	
	/**
     * 列表
     * @return
     */
    public Page getfindFullTrialCode(String pageSize);
}