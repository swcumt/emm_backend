package com.nationsky.api.v1;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.CoolIdea;
import com.nationsky.model.FrontUser;
import com.nationsky.model.Users;
import com.nationsky.service.CoolIdeaManager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/coolIdea*")
@Scope("prototype")
public class ApiCoolIdeaController extends BaseFormController {

	private CoolIdeaManager coolIdeaManager = null;

	@Autowired
	public void setCoolIdeaManager(CoolIdeaManager coolIdeaManager) {
		this.coolIdeaManager = coolIdeaManager;
	}

	@Autowired
	private Root root;

	/**
	 * 最新
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/newest")
	public @ResponseBody
	Root getNewest() {
		try{
			List<CoolIdea> coolIdeaList = coolIdeaManager.getAllNewest();
			for (CoolIdea coolIdea : coolIdeaList) {
				FrontUser users = coolIdea.getUsers();
				users.setPassword("");
				coolIdea.setUsers(users);
			}
			root.setObject(coolIdeaList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}

	/**
	 * 最热
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/heatest")
	public @ResponseBody
	Root getHeatest() {
		List<CoolIdea> coolIdeaList = coolIdeaManager.getAllHeatest();
		for (CoolIdea coolIdea : coolIdeaList) {
			FrontUser users = coolIdea.getUsers();
			users.setPassword("");
			coolIdea.setUsers(users);
		}
		root.setObject(coolIdeaList);
		return root;
	}

	/**
	 * 通过状态获取列表(0:待审阅,1:已采纳,2:已拒绝)
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/flag/{flag}")
	public @ResponseBody
	Root getAllByFlag(@PathVariable String flag) {
		List<CoolIdea> coolIdeaList = coolIdeaManager.getAllByFlag(flag);
		for (CoolIdea coolIdea : coolIdeaList) {
			FrontUser users = coolIdea.getUsers();
			users.setPassword("");
			coolIdea.setUsers(users);
		}
		root.setObject(coolIdeaList);
		return root;
	}

	/**
	 * 详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/ideadetail/{id}")
	public @ResponseBody
	Root getDetail(@PathVariable String id) {
		CoolIdea idea = new CoolIdea();
		if (!StringUtils.isBlank(id)) {
			try {
				idea = coolIdeaManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}

		root.setObject(idea);
		return root;
	}

	/**
	 * 添加
	 * 
	 * @param ideas
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public @ResponseBody
	Root add(CoolIdea ideas) {
		ideas.setCreateTime(new Date());
		ideas.setCommentNumber(0);
		ideas.setPraiseNumber(0);
		ideas.setFlag(0);
		try{
			CoolIdea idea = coolIdeaManager.save(ideas);
			if (idea == null) {
				root.setMessage(1, "添加失败");
			} else {
				root.setMessage("添加成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
			root.setMessage(1, "添加失败");
		}
		
		return root;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public @ResponseBody
	Root update(CoolIdea ideas) {
		try{
			if(ideas.getFlag() != null){
				CoolIdea idea = coolIdeaManager.get(ideas.getId());
				idea.setFlag(ideas.getFlag());
				CoolIdea coolIdea = coolIdeaManager.save(idea);
				if (coolIdea == null) {
					root.setMessage(1, "修改失败");
				} else {
					root.setMessage("修改成功");
				}
			}else{
				root.setMessage("修改失败");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			root.setMessage(1, "修改失败");
		}
		
		return root;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	Root delete(@PathVariable String id) {
		if (!StringUtils.isBlank(id)) {
			try {
				coolIdeaManager.remove(new Long(id));
				root.setMessage(0, "删除成功");
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}
		return root;
	}

}
