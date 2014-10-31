package com.nationsky.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.CodeAppStatus;
import com.nationsky.service.CodeAppStatusManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/codeAppStatus")
@Scope("prototype")
public class CodeAppStatusController extends BaseFormController {
	@Autowired
	private CodeAppStatusManager codeAppStatusManager = null;

	@Autowired
	private Root root;

	@RequestMapping(value = "/isExistsText",method = RequestMethod.POST)
	public @ResponseBody
	Root isExistsText(CodeAppStatus codeAppStatus) {
		if(codeAppStatusManager.exists(codeAppStatus)){
			root.setCode(-1);
		}
		return root;
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Root getAll(String pageSize) {
		Page page = codeAppStatusManager.codeAppStatusList(pageSize);
		root.setObject(page.getObjList());
		root.setPageCount(page.getCountPage());
		return root;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Root save(CodeAppStatus codeAppStatus) {
		// CodeAppStatus appStatus =
		// codeAppStatusManager.exists(codeAppStatus.getText());
		// if (appStatus != null) {
		// root.setMessage(1, "此名称已存在");
		// } else {
		CodeAppStatus appStatus = codeAppStatusManager.save(codeAppStatus);
		if (appStatus == null) {
			root.setMessage(1, "保存失败");
		} else {
			if(codeAppStatus.getId() != null && !"".equals(codeAppStatus.getId())){
  				root.setMessage("修改成功");
  			}else{
  				root.setMessage("添加成功");
  			}
		}
		// }
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
		codeAppStatusManager.remove(id);
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
		CodeAppStatus codeAppStatus = codeAppStatusManager.get(id);
		root.setObject(codeAppStatus);
		return root;
	}
}
