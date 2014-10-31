package com.nationsky.api.v1;

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
import com.nationsky.model.FrontGroupRelation;
import com.nationsky.model.FrontUser;
import com.nationsky.model.FrontUserGroup;
import com.nationsky.service.FrontGroupRelationManager;
import com.nationsky.service.FrontUserGroupManager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/groupRelation*")
@Scope("prototype")
public class ApiFrontGroupRelationController extends BaseFormController {

	private FrontGroupRelationManager groupRelationManager;
	private FrontUserGroupManager frontUserGroupManager;
	@Autowired
	public void setGroupRelationManager(
			FrontGroupRelationManager groupRelationManager) {
		this.groupRelationManager = groupRelationManager;
	}
	@Autowired
	public void setFrontUserGroupManager(FrontUserGroupManager frontUserGroupManager) {
		this.frontUserGroupManager = frontUserGroupManager;
	}
	@Autowired
	private Root root;
	
	/**
     * 用户添加至分组
     */
	@RequestMapping(method = RequestMethod.POST ,value = "/add")
    public @ResponseBody Root addRelation(FrontGroupRelation groupRelation,HttpServletRequest request){
			FrontGroupRelation frontGroupRelation = groupRelationManager.find(groupRelation.getGroup().getId(),groupRelation.getUser().getId());
			if(frontGroupRelation == null){
				FrontGroupRelation relation = groupRelationManager.save(groupRelation);
				if(relation == null){
					root.setMessage(1, "添加失败");
				}else{
					FrontUserGroup group = frontUserGroupManager.get(groupRelation.getGroup().getId());
					group.setCount(group.getCount() + 1);
					frontUserGroupManager.save(group);
					root.setMessage("添加成功");
					root.setObject(relation);
				}
			}else{
				root.setMessage(2, "用户已在该分组");
			}
		return root;
    }
	/**
	 * 用户移动分组
	 */
	@RequestMapping(method = RequestMethod.POST ,value = "/move")
	public @ResponseBody Root move(String userId,String fromGroupId,String toGroupId){
		FrontGroupRelation newRelation = new FrontGroupRelation();
		FrontUser user = new FrontUser();
		user.setId(userId);
		FrontUserGroup newGroup = new FrontUserGroup();
		newGroup.setId(Long.valueOf(toGroupId));
		newRelation.setGroup(newGroup);
		newRelation.setUser(user);
		
		boolean flag = groupRelationManager.delete(fromGroupId,userId);
		if(flag){
			FrontGroupRelation relation = groupRelationManager.save(newRelation);
			if(relation != null){
				newGroup = frontUserGroupManager.get(newGroup.getId());
				newGroup.setCount(newGroup.getCount() + 1);
				FrontUserGroup save = frontUserGroupManager.save(newGroup);
				FrontUserGroup oldGroup = frontUserGroupManager.get(Long.valueOf(fromGroupId));
				oldGroup.setCount(oldGroup.getCount() - 1);
				FrontUserGroup save2 = frontUserGroupManager.save(oldGroup);
				root.setMessage("移动成功");
			}else{
				root.setMessage(1, "移动失败");
			}
		}else{
			root.setMessage(1, "移动失败");
		}
		return root;
	}
	/**
     * 分组删除用户
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/{groupId}/{userId}")
    public @ResponseBody Root  deleteGroup(@PathVariable String groupId,@PathVariable String userId){
    	  if (!StringUtils.isBlank(groupId) && !StringUtils.isBlank(userId)) {
    		  try {
    			  boolean flag = groupRelationManager.delete(groupId,userId);
    			  if(flag){
    				  root.setMessage(0, "删除成功");
    				  FrontUserGroup group = frontUserGroupManager.get(Long.valueOf(groupId));
    				  group.setCount(group.getCount() - 1);
    				  frontUserGroupManager.save(group);
    			  }else{
    				  root.setError("数据不存在");
    			  }
			  } catch (Exception e) {
				 root.setError("数据不存在");
			  }
          }else{
        	  root.setMessage(1, "参数错误");
          }
		return root;
    }	
}
