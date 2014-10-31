package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CodeDeveloper;

@WebService
public interface CodeDeveloperManager extends GenericManager<CodeDeveloper, Long> {
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	public CodeDeveloper exists(String text);
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	boolean exists(CodeDeveloper codeOs);
}