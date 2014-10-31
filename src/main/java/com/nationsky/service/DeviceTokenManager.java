package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.DeviceToken;

@WebService
public interface DeviceTokenManager extends GenericManager<DeviceToken, Long> {
	/**
	 * 根据应用DI获取安装该应用的所有DeviceToken
	 */
	List<DeviceToken> getAllByAppId(Long app_version_id);
	/**
	 * 根据应用DI获取安装该应用的所有DeviceToken
	 */
	List<String> getTokenByAppId(Long app_version_id);
}