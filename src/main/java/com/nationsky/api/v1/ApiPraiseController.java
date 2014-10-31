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
import com.nationsky.model.Praise;
import com.nationsky.service.CoolIdeaManager;
import com.nationsky.service.PraiseManager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/prais*")
@Scope("prototype")
public class ApiPraiseController extends BaseFormController {

	private PraiseManager praiseManager;
	private CoolIdeaManager coolIdeaManager = null;
	
	@Autowired
	public void setPraiseManager(PraiseManager praiseManager) {
		this.praiseManager = praiseManager;
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
	Root add(Praise praise) {
		praise.setCreateTime(new Date());
		CoolIdea coolIdea = coolIdeaManager.get(praise.getCoolIdeaId());
		Praise praised = praiseManager.save(praise);
		if (praised == null) {
			root.setMessage(1, "添加失败");
		} else {
			coolIdea.setPraiseNumber(coolIdea.getPraiseNumber() + 1);
			coolIdeaManager.save(coolIdea);
			root.setMessage("添加成功");
		}
		return root;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{ideaId}")
	public @ResponseBody
	Root getIdeaComments(@PathVariable String ideaId) {
		List<Praise> praiseList = null;
    	if(!StringUtils.isBlank(ideaId)){
    		try {
    			praiseList = praiseManager.findPraiseList(ideaId);
    			root.setObject(praiseList);
			} catch (Exception e) {
				e.printStackTrace();
				root.setError("数据不存在");
			}
    	}
    	return root;
	}
	
	
}
