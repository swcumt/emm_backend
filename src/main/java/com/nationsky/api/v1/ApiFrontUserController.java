package com.nationsky.api.v1;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.nationsky.entity.Root;
import com.nationsky.model.FrontUser;
import com.nationsky.service.FrontGroupRelationManager;
import com.nationsky.service.FrontUserGroupManager;
import com.nationsky.service.FrontUserManager;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Tools;
import com.nationsky.webapp.util.Utils;

@Controller
@RequestMapping("/v1/frontUser*")
@Scope("prototype")
public class ApiFrontUserController extends BaseFormController {

	private FrontUserManager frontUserManager;
	private FrontGroupRelationManager groupRelationManager;
	private FrontUserGroupManager frontUserGroupManager;
	@Autowired
	public void setFrontUserManager(FrontUserManager frontUserManager) {
		this.frontUserManager = frontUserManager;
	}
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
	  * 用户列表
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.POST,value="/list")
	 public @ResponseBody Root list(){
	 	 List<FrontUser> userList = frontUserManager.getAll();
	 	 root.setObject(userList);
	 	return root;
	 }
	 
	  /**
	     * 添加用户
	     */
		@RequestMapping(method = RequestMethod.POST , value = "/add")
	    public @ResponseBody Root addUser(FrontUser users,HttpServletRequest request){
			boolean isExist = frontUserManager.findLoginNameIsExist(users.getLoginName());
			if(!isExist){
				ServletContext servletContext = request.getSession().getServletContext();
				String projectPath = servletContext.getRealPath("/");
				String projectName = servletContext.getContextPath();
				projectName = projectName.substring(1, projectName.length());
				String tomcatWebappsPath = projectPath.substring(0, projectPath.indexOf(projectName));
				String serverAddr = Utils.getPropertiesValue("emm_backend_server");
				String emm_backend_static = Utils.getPropertiesValue("emm_backend_static");
				String iconSavePath = File.separatorChar + "usericon" + File.separatorChar;
				String icon = users.getIcon();
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
				//判断 request 是否有文件上传,即多部分请求  
				if(multipartResolver.isMultipart(request)){  
					MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
					//取得request中的所有文件名  
					Iterator<String> iter = multiRequest.getFileNames();  
					while(iter.hasNext()){  
						//取得上传文件  
						MultipartFile file = multiRequest.getFile(iter.next());  
						if(file != null){  
							//取得当前上传文件的文件名称  
							String myFileName = file.getOriginalFilename();  
							//如果名称不为“”,说明该文件存在，否则说明该文件不存在  
							if(myFileName.trim() !=""){  
								System.out.println(myFileName);  
								//重命名上传后的文件名  
								String originalFileName = file.getOriginalFilename();  
								String iconRealPath = "";
								if(originalFileName.indexOf(".png") != -1 || originalFileName.indexOf(".jpg") != -1 || originalFileName.indexOf(".JPG") != -1){
									String zip =originalFileName.substring(originalFileName.lastIndexOf('.'), originalFileName.length());
									// 保存路径
									String fileName = Utils.getUUID() + zip;
									iconRealPath = tomcatWebappsPath + emm_backend_static + iconSavePath + fileName;
									String iconRomotePath = serverAddr + emm_backend_static + iconSavePath + fileName;
									icon = iconRomotePath = iconRomotePath.replaceAll("\\\\", "/");
								}
								File localFile = new File(iconRealPath);  
								try {
									file.transferTo(localFile);
								} catch (Exception e) {
									e.printStackTrace();
									root.setError("文件上传出错");
								}  
							}  
						} 
					}
					users.setIcon(icon);
				}
				if(users.getId() == null){
					users.setId(Tools.getuuid());
					users.setScore(0);
					users.setSeriesLoginCount(0);
				}
				FrontUser user = frontUserManager.save(users);
				if(user == null){
					root.setMessage(1, "添加失败");
				}else{
					root.setMessage("添加成功");
					root.setObject(user);
				}
			}else{
				root.setMessage(2, "该账号已存在");
			}
			return root;
	    }
		/**
		 * 修改用户头像
		 */
		@RequestMapping(method = RequestMethod.POST , value = "/updateIcon")
		public @ResponseBody Root updateUserIcon(String id,HttpServletRequest request){
			if(!StringUtils.isBlank(id)){
				FrontUser users = frontUserManager.get(id);
				if(users != null){
					ServletContext servletContext = request.getSession().getServletContext();
					String projectPath = servletContext.getRealPath("/");
					String projectName = servletContext.getContextPath();
					projectName = projectName.substring(1, projectName.length());
					String tomcatWebappsPath = projectPath.substring(0, projectPath.indexOf(projectName));
					String serverAddr = Utils.getPropertiesValue("emm_backend_server");
					String emm_backend_static = Utils.getPropertiesValue("emm_backend_static");
					String iconSavePath = File.separatorChar + "usericon" + File.separatorChar;
					String icon = users.getIcon();
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
					//判断 request 是否有文件上传,即多部分请求  
					if(multipartResolver.isMultipart(request)){  
						MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
						//取得request中的所有文件名  
						Iterator<String> iter = multiRequest.getFileNames();  
						while(iter.hasNext()){  
							//取得上传文件  
							MultipartFile file = multiRequest.getFile(iter.next());  
							if(file != null){  
								//取得当前上传文件的文件名称  
								String myFileName = file.getOriginalFilename();  
								//如果名称不为“”,说明该文件存在，否则说明该文件不存在  
								if(myFileName.trim() !=""){  
									System.out.println(myFileName);  
									//重命名上传后的文件名  
									String originalFileName = file.getOriginalFilename();  
									String iconRealPath = "";
									if(originalFileName.indexOf(".png") != -1 || originalFileName.indexOf(".jpg") != -1 || originalFileName.indexOf(".JPG") != -1){
										String zip =originalFileName.substring(originalFileName.lastIndexOf('.'), originalFileName.length());
										// 保存路径
										String fileName = Utils.getUUID() + zip;
										iconRealPath = tomcatWebappsPath + emm_backend_static + iconSavePath + fileName;
										String iconRomotePath = serverAddr + emm_backend_static + iconSavePath + fileName;
										icon = iconRomotePath = iconRomotePath.replaceAll("\\\\", "/");
									}
									File localFile = new File(iconRealPath);  
									try {
										file.transferTo(localFile);
									} catch (Exception e) {
										e.printStackTrace();
										root.setError("文件上传出错");
									}  
								}  
							} 
						}
						users.setIcon(icon);
						FrontUser user = frontUserManager.save(users);
						if(user == null){
							root.setMessage(1, "修改失败");
						}else{
							root.setMessage("修改成功");
							root.setObject(user);
						}
					}else{
						root.setMessage(2, "未选择头像");
					}
				}else{
					root.setMessage(3, "用户不存在");
				}
			}else{
				root.setMessage(4, "参数错误");
			}
			return root;
		}
		/**
		 * 修改用户密码
		 */
		@RequestMapping(method = RequestMethod.POST , value = "/updatePassword")
		public @ResponseBody Root updateUserPassword(String id,String oldPassword,String newPassword){
			if(!StringUtils.isBlank(id)){
				FrontUser users = frontUserManager.get(id);
				if(users != null){
					if(users.getPassword().equals(oldPassword)){
						users.setPassword(newPassword);
						FrontUser save = frontUserManager.save(users);
						if(save != null){
							root.setMessage(1, "密码修改成功");
						}else{
							root.setMessage("密码修改失败");
						}
					}else{
						root.setMessage(2, "用户原始密码输入错误");
					}
				}else{
					root.setMessage(3, "用户不存在");
				}
			}else{
				root.setMessage(4, "参数错误");
			}
			return root;
		}
		
		 /**
	     * 查询用户详细信息
	     * @param request
	     * @param response
	     * @return 
	     */
	    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
	    public @ResponseBody  Root userInfo(@PathVariable String id){
	    	FrontUser user = null;
	    	  if (!StringUtils.isBlank(id)) {
	    		  try {
	    			  user =  frontUserManager.get(id);
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
	    			  frontUserManager.remove(id);
	    			  root.setMessage(0, "删除成功");
				  } catch (Exception e) {
					 root.setError("数据不存在");
				  }
	          }
			return root;
	    }
	    
	    /**
		  * 获取分组用户列表
		  * @return
		  */
		 @RequestMapping(method=RequestMethod.POST,value="/group/{groupId}")
		 public @ResponseBody Root listByGroupId(@PathVariable String groupId){
			 try{
				 List<FrontUser> userList = frontUserManager.getGroupUsers(groupId);
				 root.setObject(userList);
			 }catch (Exception e) {
				e.printStackTrace();
			}
		 	return root;
		 }
		 /**
		  * test 方法
		  * @return
		  */
		 @RequestMapping(method=RequestMethod.POST,value="/addall")
		 public @ResponseBody Root addall(){
			 try{
				 frontUserManager.addall();
			 }catch (Exception e) {
				 e.printStackTrace();
			 }
			 return root;
		 }
	
}
