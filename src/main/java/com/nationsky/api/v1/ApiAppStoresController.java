package com.nationsky.api.v1;

import java.io.File;

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
import com.nationsky.model.AppProject;
import com.nationsky.model.AppStore;
import com.nationsky.service.AppProjectManager;
import com.nationsky.service.AppStoreManager;
import com.nationsky.vo.AppVO;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.HttpUtils;
import com.nationsky.webapp.util.Img;
import com.nationsky.webapp.util.Utils;

/**
 * 应用管理
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/v1/appStoresform*")
@Scope("prototype")
public class ApiAppStoresController extends BaseFormController {
	
	@Autowired
	private AppProjectManager appProjectManager;
	
	private AppStoreManager appStoreManager = null;

	@Autowired
	public void setAppStoreManager(AppStoreManager appStoreManager) {
		this.appStoreManager = appStoreManager;
	}

	@Autowired
	private Root root;

	/**
	 * 添加
	 * 
	 * @param version
	 * @param files
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public @ResponseBody
	Root add(MultipartFile iconFile,MultipartFile logoFile, AppStore appStore, HttpServletRequest request) throws NumberFormatException, Exception {
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
			String orgName = iconFile.getOriginalFilename();
			String zip = orgName.substring(orgName.lastIndexOf('.'), orgName.length());
			// 保存路径
			String fileName = Utils.getUUID() + zip;
			String iconRealPath = tomcatWebappsPath + emm_backend_static + iconSavePath;
			File localFile = new File(iconRealPath+fileName);
			iconFile.transferTo(localFile);
			String iconName = Img.receiveImg(fileName, iconRealPath, localFile, Integer.parseInt(Utils.getPropertiesValue("appIconWidth")), Integer.parseInt(Utils.getPropertiesValue("appIconHeight")));
			String iconInfoName = Img.receiveImg(fileName, iconRealPath, localFile, Integer.parseInt(Utils.getPropertiesValue("appIconInfoWidth")), Integer.parseInt(Utils.getPropertiesValue("appIconInfoHeight")));
			if(localFile.exists()){
				localFile.delete();
			}
			String iconRomotePath = serverAddr + emm_backend_static + iconSavePath + iconName;
			iconRomotePath = iconRomotePath.replaceAll("\\\\", "/");
			String iconInfoRomotePath = serverAddr + emm_backend_static + iconSavePath + iconInfoName;
			iconInfoRomotePath = iconInfoRomotePath.replaceAll("\\\\", "/");

			appStore.setIcon(iconRomotePath);
			appStore.setIconInfo(iconInfoRomotePath);
			
			
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
			appStore.setDeveloperLogo(iconRomotePath = iconRomotePath.replaceAll("\\\\", "/"));
		}

		//如果选择的CodeOsStyle=1,就不需要保存操作系统.
		if(appStore!=null && appStore.getCodeOsStyle()!=null && appStore.getCodeOsStyle().getId().toString().equals("1")){
			appStore.setCodeOs(null);
		}
		
		AppProject appProject = appProjectManager.get(appStore.getAppProject().getId());
		
		appStore.setAppType(appProject.getAppType());
		AppStore app = appStoreManager.save(appStore);
		if (app == null) {
			root.setMessage(1, "添加失败");
		} else {
			if(appStore.getId() != null && !"".equals(appStore.getId())){
				root.setMessage("修改成功");
			}else{
				root.setMessage("添加成功");
			}
		}
		return root;
	}

	/**
	 * 详细信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody
	Root detail(@PathVariable String id) {
		AppStore appStore = new AppStore();
		if (!StringUtils.isBlank(id)) {
			try {
				appStore = appStoreManager.get(new Long(id));
				appStore.setAppstoreEditions(null);
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}

		root.setObject(appStore);
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
				appStoreManager.remove(new Long(id));
				root.setMessage("删除成功");
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}
		return root;
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public @ResponseBody
	Root list(String pageSize,HttpServletRequest request,Long projectId,Long osId) {
//		Page page = appStoreManager.getAppStore(pageSize);
		Page page;
		if (projectId != null && !projectId.toString().equals("")){
			//查询项目下所有应用
			page = appStoreManager.getAppStoreByProject(pageSize,projectId,osId);
		}else{
			//查询用户下所有应用
			page = appStoreManager.getAppStoreByUser(pageSize,Utils.getUser(request).getId(),osId);
		}
		for (int i = 0; i < page.getObjList().size(); i++) {
			AppStore appStore = (AppStore) page.getObjList().get(i);
			appStore.setAppstoreEditions(null);
		}
		root.setObject(page.getObjList());
		root.setPageCount(page.getCountPage());
		return root;
	}

	/**
	 * appstore 查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/appdetail/{id}")
	public @ResponseBody
	Root appdetail(@PathVariable String id) {
		AppVO app = HttpUtils.appdetail(id);
		root.setObject(app);
		return root;
	}

}
