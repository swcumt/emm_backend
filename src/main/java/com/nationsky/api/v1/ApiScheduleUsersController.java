package com.nationsky.api.v1;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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

import com.nationsky.entity.Root;
import com.nationsky.model.ScheduleUser;
import com.nationsky.service.ScheduleUserManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;
/**
 * 值班人员
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/v1/scheduleUser*")
@Scope("prototype")
public class ApiScheduleUsersController extends BaseFormController {
    private ScheduleUserManager scheduleUserManager = null;

    @Autowired
    public void setScheduleUserManager(ScheduleUserManager scheduleUserManager) {
        this.scheduleUserManager = scheduleUserManager;
    }
    
    @Autowired
    private Root root;

    /**
     * 添加
     * @param scheduleUser
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
    @RequestMapping(method=RequestMethod.POST)
   public @ResponseBody Root add(MultipartFile iconFile,ScheduleUser scheduleUser, HttpServletRequest request) throws IllegalStateException, IOException{
    	ServletContext servletContext = request.getSession().getServletContext();
		String projectPath = servletContext.getRealPath("/");
		String projectName = servletContext.getContextPath();
		projectName = projectName.substring(1, projectName.length());
		String tomcatWebappsPath = projectPath.substring(0, projectPath.indexOf(projectName));
		String serverAddr = Utils.getPropertiesValue("emm_backend_server");
		String emm_backend_static = Utils.getPropertiesValue("emm_backend_static");
		String iconSavePath = File.separatorChar + "appImages" + File.separatorChar;
		
		if (iconFile != null && !iconFile.isEmpty()) {
			// 重命名上传后的文件名
			String originalFileName = iconFile.getOriginalFilename();
			String zip = originalFileName.substring(originalFileName.lastIndexOf('.'), originalFileName.length());
			// 保存路径
			String fileName = Utils.getUUID() + zip;
			String iconRealPath = tomcatWebappsPath + emm_backend_static + iconSavePath + fileName;
			String iconRomotePath = serverAddr + emm_backend_static + iconSavePath + fileName;
			File localFile = new File(iconRealPath);
			iconFile.transferTo(localFile);
			iconRomotePath = iconRomotePath.replaceAll("\\\\", "/");
			scheduleUser.setUserIcon(iconRomotePath);
		}
		
    	scheduleUser.setCreateTime(new Date());
	    ScheduleUser user = scheduleUserManager.save(scheduleUser);
	   if(user == null){
		   root.setMessage(1, "添加失败");
	   }else{
		   if(scheduleUser.getId() != null && !"".equals(scheduleUser.getId())){
				root.setMessage("修改成功");
			}else{
				root.setMessage("添加成功");
			}
	   }
	   return root;
   }
    
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
    public @ResponseBody Root delete(@PathVariable String id){
    	if(!StringUtils.isBlank(id)){
    		try {
				scheduleUserManager.remove(new Long(id));
				root.setMessage("删除成功");
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	return root;
    }
    
    /**
     * 值班人员列表
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/list")
    public @ResponseBody Root list(String pageSize){
    	Page page= scheduleUserManager.findScheduleUser(pageSize);
    	root.setObject(page.getObjList());
    	root.setPageCount(page.getCountPage());
    	return root;
    }
    
    /**
     * 值班人员列表select2
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/listUser")
    public @ResponseBody Root listUser(String flag,String userId){
    	List<ScheduleUser> userList= scheduleUserManager.findScheduleUsers(flag,userId);
    	root.setObject(userList);
    	return root;
    }
    
    /**
     * 值班人员详细
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/{id}")
    public @ResponseBody Root detail(@PathVariable String id){
    	ScheduleUser scheduleUser = null;
    	if(!StringUtils.isBlank(id)){
    		try {
    			scheduleUser = scheduleUserManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	root.setObject(scheduleUser);
    	return root;
    }
}
