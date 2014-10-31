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
import com.nationsky.model.IdeaComment;
import com.nationsky.model.Users;
import com.nationsky.service.CoolIdeaManager;
import com.nationsky.service.IdeaCommentManager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/ideaComment*")
@Scope("prototype")
public class ApiIdeaCommentController extends BaseFormController {
	private IdeaCommentManager ideaCommentManager = null;
	private CoolIdeaManager coolIdeaManager = null;
	@Autowired
	public void setIdeaCommentManager(IdeaCommentManager ideaCommentManager) {
		this.ideaCommentManager = ideaCommentManager;
	}
	@Autowired
	public void setCoolIdeaManager(CoolIdeaManager coolIdeaManager) {
		this.coolIdeaManager = coolIdeaManager;
	}

	@Autowired
	private Root root;

	/**
	 * 添加
	 * 
	 * @param ideas
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public @ResponseBody
	Root add(IdeaComment comments) {
		comments.setCreateTime(new Date());
		CoolIdea coolIdea = coolIdeaManager.get(comments.getCoolIdeaId());
		coolIdea.setCommentTime(new Date());
		
		IdeaComment comment = ideaCommentManager.save(comments);
		if (comment == null) {
			root.setMessage(1, "添加失败");
		} else {
			coolIdea.setCommentNumber(coolIdea.getCommentNumber() + 1);
			root.setMessage("添加成功");
		}
		coolIdeaManager.save(coolIdea);
		return root;
	}
	/**
	 * 获取酷点子评论
	 * 
	 * @param ideaId 酷点子id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{ideaId}")
	public @ResponseBody
	Root getIdeaComments(@PathVariable String ideaId) {
		List<IdeaComment> ideaCommentList = null;
    	if(!StringUtils.isBlank(ideaId)){
    		try {
    			ideaCommentList = ideaCommentManager.findIdeaCommentList(ideaId);
    			for (IdeaComment ideaComment : ideaCommentList) {
    				FrontUser users = ideaComment.getUser();
    				users.setPassword("");
    				ideaComment.setUser(users);
				}
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	root.setObject(ideaCommentList);
    	return root;
	}
	
}
