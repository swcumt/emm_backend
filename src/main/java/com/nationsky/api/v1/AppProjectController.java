package com.nationsky.api.v1;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.AppProject;
import com.nationsky.model.Users;
import com.nationsky.service.AppProjectManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;

@Controller
@RequestMapping("/v1/appProject")
@Scope("prototype")
public class AppProjectController extends BaseFormController {
	@Autowired
	private AppProjectManager appProjectManager = null;

	@Autowired
	private Root root;


	@RequestMapping(value = "/isExistsText",method = RequestMethod.POST)
	public @ResponseBody
	Root isExistsText(AppProject appProject,HttpServletRequest request) {
		if(appProjectManager.exists(appProject,Utils.getUser(request).getId())){
			root.setCode(-1);
		}
		return root;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Root getAll(String pageSize,HttpServletRequest request) {
		Users users = Utils.getUser(request);
		Page page = appProjectManager.getList(pageSize,users.getId());
		root.setObject(page.getObjList());
		root.setPageCount(page.getCountPage());
		return root;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Root save(AppProject appProject,HttpServletRequest request) {
		if (appProjectManager.exists(appProject,Utils.getUser(request).getId())) {
			root.setMessage(2, "此名称已存在");
			return root;
		}
		appProject.setCreateTime(new Date());
		appProject.setUsers(Utils.getUser(request));
		AppProject os = appProjectManager.save(appProject);
		if (os != null) {
			root.setMessage("保存成功");
		} else {
			root.setMessage(1,"保存失败");
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
		appProjectManager.remove(id);
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
		AppProject codeOs = appProjectManager.get(id);
		root.setObject(codeOs);
		return root;
	}
}
