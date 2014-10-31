package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CodeOs;
import com.nationsky.model.FullTrialCode;
import com.nationsky.vo.Page;

@WebService
public interface FullTrialCodeManager extends GenericManager<FullTrialCode, Long> {
	/**
	 * 查询版本是否存在
	 * @param text
	 * @return
	 */
    public FullTrialCode findFullTrialCode(String text);
    
    /**
     * 列表
     * @return
     */
    public Page getfindFullTrialCode(String pageSize);
    
    /**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	boolean exists(FullTrialCode fullTrialCode);
}