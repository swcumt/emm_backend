package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CodeModel;
import com.nationsky.vo.Page;

@WebService
public interface CodeModelManager extends GenericManager<CodeModel, Long> {
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	boolean exists(CodeModel codeOs);
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	CodeModel exists(String text);

	/**
	 * 系统版本型号列表
	 * @param pageSize
	 * @return
	 */
	public Page codeOsList(String pageSize);
}