package com.nationsky.api.v1;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
import com.nationsky.model.Schedule;
import com.nationsky.service.ScheduleManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;
/**
 * 服务类管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/v1/schedule*")
@Scope("prototype")
public class ApiSchedulesController extends BaseFormController {
    private ScheduleManager scheduleManager = null;

    @Autowired
    public void setScheduleManager(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }

   @Autowired
    private Root root;
   
   /**
    * 添加服务类
    * @param schedule
    * @return
 * @throws IOException 
 * @throws IllegalStateException 
    */
   @RequestMapping(method=RequestMethod.POST)
   public @ResponseBody Root add(MultipartFile iconFile, MultipartFile logoFile,Schedule schedule,HttpServletRequest request) throws IllegalStateException, IOException{
	    ServletContext servletContext = request.getSession().getServletContext();
	    String projectPath = servletContext.getRealPath("/");
		String projectName = servletContext.getContextPath();
		projectName = projectName.substring(1, projectName.length());
		String tomcatWebappsPath = projectPath.substring(0, projectPath.indexOf(projectName));
		String serverAddr = Utils.getPropertiesValue("emm_backend_server");
		String emm_backend_static = Utils.getPropertiesValue("emm_backend_static");
		String iconSavePath = File.separatorChar + "serverType" + File.separatorChar;
		 
		if (iconFile != null && !iconFile.isEmpty()) {
			// 重命名上传后的文件名
			String orgName = iconFile.getOriginalFilename();
			String zip = orgName.substring(orgName.lastIndexOf('.'), orgName.length());
			// 保存路径
			String fileName = Utils.getUUID() + zip;
			String iconRealPath = tomcatWebappsPath + emm_backend_static + iconSavePath;
			File file = new File(iconRealPath);
			if(!file.exists()){
				file.mkdir();
			}
			iconRealPath = iconRealPath+fileName;
			String iconRomotePath = serverAddr + emm_backend_static + iconSavePath + fileName;
			iconRomotePath = iconRomotePath.replaceAll("\\\\", "/");
			File localFile = new File(iconRealPath);

			schedule.setIcon(iconRomotePath);
			iconFile.transferTo(localFile);
		}

		if (logoFile != null && !logoFile.isEmpty()) {
			// 重命名上传后的文件名
			String originalFileName = logoFile.getOriginalFilename();
			String zip = originalFileName.substring(originalFileName.lastIndexOf('.'), originalFileName.length());
			// 保存路径
			String fileName = Utils.getUUID() + zip;
			String iconRealPath = tomcatWebappsPath + emm_backend_static + iconSavePath + fileName;
			String iconRomotePath = serverAddr + emm_backend_static + iconSavePath + fileName;
			File localFile = new File(iconRealPath);
			logoFile.transferTo(localFile);
			schedule.setIcons(iconRomotePath = iconRomotePath.replaceAll("\\\\", "/"));
		}
	   schedule.setCreateTime(new Date());
	   Schedule sche = scheduleManager.save(schedule);
	   if(sche!=null){
		   if(schedule.getId() != null && !"".equals(schedule.getId())){
				root.setMessage("修改成功");
			}else{
				root.setMessage("添加成功");
			}
	   }else{
		   root.setMessage(1, "添加失败");
	   }
	   return root;
   }
   
   /**
    * 服务类列表
    * @return
    */
   @RequestMapping(method=RequestMethod.POST,value="/list")
   public @ResponseBody Root list(String pageSize){
	   Page page = scheduleManager.findShceduleList(pageSize);
	   root.setObject(page.getObjList());
	   root.setPageCount(page.getCountPage());
	   return root;
   }
   
   /**
    * 删除服务类
    * @param id
    * @return
    */
   @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
   public @ResponseBody Root delete(@PathVariable String id){
	   if(!StringUtils.isBlank(id)){
		   try {
			   scheduleManager.remove(new Long(id));
			   root.setMessage("删除成功");
		} catch (Exception e) {
			root.setError("数据不存在");
		}
	   }
	   return root;
   }
   
   /**
    * 服务类详细
    * @param id
    * @return
    */
   @RequestMapping(method=RequestMethod.GET,value="/{id}")
   public @ResponseBody Root detail(@PathVariable String id){
	   Schedule schedule = null;
	   if(!StringUtils.isBlank(id)){
		   try {
			   schedule = scheduleManager.get(new Long(id));
		} catch (Exception e) {
			root.setError("数据不存在");
		}
	   }
	   root.setObject(schedule);
	   return root;
   }
   
}
