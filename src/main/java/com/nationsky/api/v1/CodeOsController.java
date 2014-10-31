package com.nationsky.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.CodeOs;
import com.nationsky.service.CodeOsManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/codeOs")
@Scope("prototype")
public class CodeOsController extends BaseFormController {
	@Autowired
	private CodeOsManager codeOsManager = null;

	@Autowired
	private Root root;


	@RequestMapping(value = "/isExistsText",method = RequestMethod.POST)
	public @ResponseBody
	Root isExistsText(CodeOs codeOs) {
		if(codeOsManager.exists(codeOs)){
			root.setCode(-1);
		}
		return root;
	}
	
	@RequestMapping(value = "/getByParaent/{pid}", method = RequestMethod.GET)
	public @ResponseBody
	Root getByParaent(Long pid) {
		List<CodeOs> codeList = codeOsManager.getAllByParentId(pid);
		root.setObject(codeList);
		return root;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Root getAll(String pageSize) {
		Page page = codeOsManager.codeOsList(pageSize);
		root.setObject(page.getObjList());
		root.setPageCount(page.getCountPage());
		return root;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Root save(CodeOs codeOs) {
		if (codeOsManager.exists(codeOs)) {
			root.setMessage(2, "此名称已存在");
		} else {
			CodeOs os = codeOsManager.save(codeOs);
			if (os == null) {
				root.setMessage(1, "保存失败");
			} else {
				if (codeOs.getId() != null && !"".equals(codeOs.getId())) {
					root.setMessage("修改成功");
				} else {
					root.setMessage("添加成功");
				}
			}
		}
		return root;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	Root delete(@PathVariable Long id) {
		codeOsManager.remove(id);
		root.setMessage("删除成功");
		return root;
	}

	/**
	 * 查看详细
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody
	Root getById(@PathVariable Long id) {
		CodeOs codeOs = codeOsManager.get(id);
		root.setObject(codeOs);
		return root;
	}
}
