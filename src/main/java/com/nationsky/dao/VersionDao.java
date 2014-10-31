package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.Version;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the Version table.
 */
public interface VersionDao extends GenericDao<Version, Long> {
	
	/**
	 * 查看最新版本
	 * @return
	 */
	public List<Version> getMaxVersion() ;
	
    /**
     * 版本列表
     * @return
     */
    public Page getVersionList(String pageSize);
}