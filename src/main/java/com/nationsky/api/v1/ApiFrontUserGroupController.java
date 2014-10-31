package com.nationsky.api.v1;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.FrontUserGroup;
import com.nationsky.service.FrontUserGroupManager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/frontUserGroup")
@Scope("prototype")
public class ApiFrontUserGroupController extends BaseFormController  {

	private FrontUserGroupManager frontUserGroupManager;
	@Autowired
	public void setFrontUserGroupManager(FrontUserGroupManager frontUserGroupManager) {
		this.frontUserGroupManager = frontUserGroupManager;
	}
	
	@Autowired
  	private Root root;
	
	/**
     * 添加或修改分组
     */
	@RequestMapping(method = RequestMethod.POST ,value = "/add")
    public @ResponseBody Root addGroup(FrontUserGroup userGroup,HttpServletRequest request){
		FrontUserGroup group = frontUserGroupManager.save(userGroup);
    	if(group == null){
    		root.setMessage(1, "添加失败");
    	}else{
    		root.setMessage("添加成功");
    		root.setObject(group);
    	}
		return root;
    }
	/**
     * 删除分组
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/{id}")
    public @ResponseBody Root  deleteGroup(@PathVariable String id){
    	  if (!StringUtils.isBlank(id)) {
    		  try {
    			  frontUserGroupManager.remove(new Long(id));
    			  root.setMessage(0, "删除成功");
			  } catch (Exception e) {
				 root.setError("数据不存在");
			  }
          }
		return root;
    }	
    
    /**
	  * 分组列表
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.POST,value="/list")
	 public @ResponseBody Root list(){
	 	 List<FrontUserGroup> groupList = frontUserGroupManager.getAll();
	 	 root.setObject(groupList);
	 	return root;
	 }
}
