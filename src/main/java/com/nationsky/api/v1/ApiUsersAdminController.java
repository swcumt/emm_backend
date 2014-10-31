package com.nationsky.api.v1;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.Users;
import com.nationsky.service.MdmUsersManager;
import com.nationsky.service.UsersManager;
import com.nationsky.utils.Crypt;
import com.nationsky.vo.MdmUser;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.MD5;

/**
 * 后台用户管理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/usersadmin*")
@Scope("prototype")
public class ApiUsersAdminController extends BaseFormController {
    private UsersManager usersManager = null;
    
    private MdmUsersManager mdmUsersManager = null;

    @Autowired
    public void setUsersManager(UsersManager usersManager) {
        this.usersManager = usersManager;
    }
    
    @Autowired
    public void setMdmUsersManager(MdmUsersManager mdmUsersManager) {
        this.mdmUsersManager = mdmUsersManager;
    }
    
    @Autowired
  	private Root root;
    
  /**
  * 用户列表
  * @return
  */
 @RequestMapping(method=RequestMethod.POST,value="/list")
 public @ResponseBody Root list(String pageSize){
 	 Page page  = usersManager.getUsersList(pageSize);
 	 root.setObject(page.getObjList());
 	 root.setPageCount(page.getCountPage());
 	return root;
 }

    /**
     * 添加用户
     */
	@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Root addUser(Users users){
		Users u = usersManager.get(users.getId());
		if(u != null){
			users.setPassword(u.getPassword());
		}
    	Users user = usersManager.save(users);
    	if(user == null){
    		root.setMessage(1, "添加失败");
    	}else{
    		//根据用户状态，添加mdm用户
    		if(user.getLoginFlag().equals(1)){
    			MdmUser mdmUser = new MdmUser();
    			mdmUser.setGroupIds("-2");
    			mdmUser.setPassword(user.getPassword());
    			mdmUser.setLoginId(user.getUsername());
    			mdmUser.setName(user.getName());
    			mdmUsersManager.insertMdmUser(mdmUser);
    		}else if(user.getLoginFlag().equals(0)){
    			mdmUsersManager.updateMdmUSER(user.getUsername());
    		}
    	    if(users.getId() != null && !"".equals(users.getId())){
  				root.setMessage("修改成功");
  			}else{
  				root.setMessage("添加成功");
  			}
    	}
		return root;
    }
    
	 /**
     * 查询管理员详细信息
     * @param request
     * @param response
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public @ResponseBody  Root userInfo(@PathVariable String id){
    	Users user = null;
    	  if (!StringUtils.isBlank(id)) {
    		  try {
    			  user =  usersManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
          }
    	  root.setObject(user);
		return root;
    }
    
    /**
     * 删除用户
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/{id}")
    public @ResponseBody Root  deleteUser(@PathVariable String id){
    	  if (!StringUtils.isBlank(id)) {
    		  try {
    			  //根据用户id查询用户详细，更改mdm用户状态
    			  Users user =  usersManager.get(new Long(id));
    			  mdmUsersManager.updateMdmUSER(user.getUsername());
    			  usersManager.remove(new Long(id));
    			  root.setMessage(0, "删除成功");
			  } catch (Exception e) {
				 root.setError("数据不存在");
			  }
          }
		return root;
    }
    
    /**
     * 查询用户密码
     */
    @RequestMapping(method = RequestMethod.POST,value="findPas")
    public @ResponseBody Root findPas(String id,String password){
    	Users u = usersManager.findUsersPas(id,password);
    	if(u == null){
    		root.setMessage(1, "旧密码错误,请重新输入");
    	}
    	return root;
    }
    
    /**
     * 修改密码
     */
	@RequestMapping(method = RequestMethod.POST,value="modifyPassword")
    public @ResponseBody Root modifyPassword(String id, String adminpassword){
		Users u = usersManager.get(Long.valueOf(id));
		if(u != null){
			u.setPassword(MD5.md5("NQ_"+adminpassword+"^Sky"));
		}
    	Users user = usersManager.save(u);
    	if(user != null){
    		root.setMessage("修改成功");
    	}else{
    		root.setMessage(1, "修改失败");
    	}
		return root;
    }
}

