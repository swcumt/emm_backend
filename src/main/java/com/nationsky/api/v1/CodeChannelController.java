package com.nationsky.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.CodeChannel;
import com.nationsky.model.CodeModel;
import com.nationsky.service.CodeChannelManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/codeChannel")
@Scope("prototype")
public class CodeChannelController extends BaseFormController {
	@Autowired
	private CodeChannelManager codeChannelManager = null;

	@Autowired
	private Root root;

	@RequestMapping(value = "/isExistsText",method = RequestMethod.POST)
	public @ResponseBody
	Root isExistsText(CodeChannel codeChannel) {
		if(codeChannelManager.exists(codeChannel)){
			root.setCode(-1);
		}
		return root;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Root getAll(String pageSize) {
		Page page =  codeChannelManager.codeChannelList(pageSize);
		root.setObject(page.getObjList());
		root.setPageCount(page.getCountPage());
		return root;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Root save(CodeChannel codeChannel) {
		// CodeChannel channel =
		// codeChannelManager.exists(codeChannel.getText());
		// if (channel != null) {
		// root.setMessage(1, "此名称已存在");
		// } else {
		CodeChannel channel = codeChannelManager.save(codeChannel);
		if (channel == null) {
			root.setMessage(1, "保存失败");
		} else {
			if(codeChannel.getId() != null && !"".equals(codeChannel.getId())){
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
		codeChannelManager.remove(id);
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
		CodeChannel codeChannel = codeChannelManager.get(id);
		root.setObject(codeChannel);
		return root;
	}
}
