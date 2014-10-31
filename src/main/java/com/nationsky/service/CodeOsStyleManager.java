package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CodeOsStyle;
import com.nationsky.vo.Page;

@WebService
public interface CodeOsStyleManager extends GenericManager<CodeOsStyle, Long> {
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	boolean exists(CodeOsStyle codeOsStyle);
	/**
	 * 检查是否存在
	 * @param text
	 * @return
	 */
	CodeOsStyle exists(String text);

	/**
	 * 根据父Id查询所有子对象
	 * @param pid 父ID
	 * @return
	 */
	List<CodeOsStyle> getAllByParentId(Long pid);
	
	/**
	 * 系统版本型号列表
	 * @param pageSize
	 * @return
	 */
	public Page codeOsStyleList(String pageSize);
}