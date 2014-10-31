package com.nationsky.dao;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.CodeChannel;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the FullTrialCode table.
 */
public interface CodeChannelDao extends GenericDao<CodeChannel, Long> {
	
	boolean exists(CodeChannel codeOs);
	
	public CodeChannel exists(String text);
	
	/**
	 * 渠道列表
	 * @param pageSize
	 * @return
	 */
	public Page codeChannelList(String pageSize);
}