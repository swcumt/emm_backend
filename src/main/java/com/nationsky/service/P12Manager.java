package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.P12;

@WebService
public interface P12Manager extends GenericManager<P12, Long> {
	/**
	 * 获取当前App版本 下所有P12文件
	 * @param appVersionId
	 * @return
	 */
	List<P12> getAllByAppId(Long appVersionId);
}