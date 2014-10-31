package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CodeOs;
import com.nationsky.vo.Page;

@WebService
public interface CodeOsManager extends GenericManager<CodeOs, Long> {
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	boolean exists(CodeOs codeOs);
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	CodeOs exists(String text);

	/**
	 * 根据父Id查询所有子对象
	 * @param pid 父ID
	 * @return
	 */
	List<CodeOs> getAllByParentId(Long pid);
	
	/**
	 * 系统版本型号列表
	 * @param pageSize
	 * @return
	 */
	public Page codeOsList(String pageSize);
}