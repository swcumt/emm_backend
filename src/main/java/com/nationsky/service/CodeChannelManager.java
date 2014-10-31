package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CodeChannel;
import com.nationsky.vo.Page;

@WebService
public interface CodeChannelManager extends GenericManager<CodeChannel, Long> {
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	boolean exists(CodeChannel codeOs);
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	public CodeChannel exists(String text);
	
	/**
	 * 渠道列表
	 * @param pageSize
	 * @return
	 */
	public Page codeChannelList(String pageSize);
}