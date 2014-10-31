package com.nationsky.service;

import java.text.ParseException;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.Version;
import com.nationsky.vo.Page;

@WebService
public interface VersionManager extends GenericManager<Version, Long> {
	/**
	 * 查看最新版本
	 * @return
	 * @throws ParseException 
	 */
    public Version getMaxVersion() throws ParseException;
    
    /**
     * 版本列表
     * @return
     */
    public Page getVersionList(String pageSize);
}