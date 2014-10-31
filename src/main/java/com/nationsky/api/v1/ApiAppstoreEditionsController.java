package com.nationsky.api.v1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;

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
import com.nationsky.model.AppstoreEdition;
import com.nationsky.service.AppstoreEditionManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.UpYunFileUpload;
import com.nationsky.webapp.util.Utils;

/**
 * 应用版本
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/appstoreEdition*")
@Scope("prototype")
public class ApiAppstoreEditionsController extends BaseFormController {
	private AppstoreEditionManager appstoreEditionManager = null;

	@Autowired
	public void setAppstoreEditionManager(
			AppstoreEditionManager appstoreEditionManager) {
		this.appstoreEditionManager = appstoreEditionManager;
	}

	@Autowired
	private Root root;

	/**
	 * 详细
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody
	Root detail(@PathVariable String id) {
		AppstoreEdition storeEdition = null;
		if (!StringUtils.isBlank(id)) {
			try {
				storeEdition = appstoreEditionManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
		}
		root.setObject(storeEdition);
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
		try {
			appstoreEditionManager.remove(new Long(id));
			root.setMessage("删除成功");
		} catch (Exception e) {
			root.setError("数据不存在");
		}
		return root;
	}

	/**
	 * 添加，修改
	 * 
	 * @param storeEdition
	 * @param files
	 * @param reques
	 * @param name 应用名称
	 * @param appStoreId 应用id
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Root add(AppstoreEdition storeEdition,String name,String pkgname,String appStoreId,HttpServletRequest request) throws IOException {
		ServletContext servletContext = request.getSession().getServletContext();
	    String projectPath = servletContext.getRealPath("/");
		String projectName = servletContext.getContextPath();
		projectName = projectName.substring(1, projectName.length());
		String tomcatWebappsPath = projectPath.substring(0, projectPath.indexOf(projectName));
		String serverAddr = Utils.getPropertiesValue("emm_backend_server");
		String emm_backend_static = Utils.getPropertiesValue("emm_backend_static");
		String ipaSavePath = File.separatorChar + "ipa" + File.separatorChar;
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
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
                    System.out.println(myFileName);
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String originalFileName = file.getOriginalFilename();  
                        String ipa = "";
                        	String zip = originalFileName.substring(originalFileName.lastIndexOf('.'), originalFileName.length());
    						String realPath = request.getRealPath("/");
    						String cname = Utils.getUUID()+storeEdition.getVersions()+zip;
    						String plistName = Utils.getUUID()+storeEdition.getVersions() + ".plist";
    						File filePath = new File(tomcatWebappsPath + emm_backend_static + ipaSavePath);
                        	if(!filePath.exists()){
                        		filePath.mkdir();
                        	}
                        	String ipaRomotePath = serverAddr + emm_backend_static + ipaSavePath + cname;
                        	ipa = tomcatWebappsPath + emm_backend_static + ipaSavePath + cname;
                        	ipaRomotePath  = ipaRomotePath.replaceAll("\\\\", "/");
    						storeEdition.setIpaUrl(ipaRomotePath);
    						storeEdition.setAppSize(file.getSize());
						if(originalFileName.indexOf(".ipa")!=-1){
    						PrintWriter p = new PrintWriter(new OutputStreamWriter(new FileOutputStream(realPath + "/download/" + plistName), "UTF-8"));
    						p.write(plistStr(ipaRomotePath , ipaRomotePath, name, pkgname).toString());
    						p.close();
    						String path = request.getSession().getServletContext().getRealPath("/") + "/download/" + plistName;
    						
    						String upYunPlistPath = "https://shiren1118.b0.upaiyun.com" + UpYunFileUpload.upYunInit(path, plistName);
    						storeEdition.setPlistUrl(upYunPlistPath);
    						
                        }
                        File localFile = new File(ipa);  
                        try {
							file.transferTo(localFile);
						} catch (Exception e) {
							e.printStackTrace();
							 root.setError("文件上传出错");
						}  
                    }  
                } 
            }
        }
        if(appStoreId!=null && !"".equals(appStoreId)){
        	storeEdition.setAppStoreId(Long.valueOf(appStoreId));
        }
		
		storeEdition.setCreateTime(new Date());
		AppstoreEdition  edition = appstoreEditionManager.save(storeEdition);
		
		if(edition == null){
			root.setMessage(1, "添加失败");
		}else{
			if(storeEdition.getId() != null && !"".equals(storeEdition.getId())){
				root.setMessage("修改成功");
			}else{
				root.setMessage("添加成功");
			}
		}
		return root;
	}
	
	/**
	 * 版本列表
	 * @param appid
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value="list/{appid}")
	public @ResponseBody Root appstoreEditionList(@PathVariable String appid,String pageSize){
		Page page = appstoreEditionManager.getAppstoreEdition(appid,pageSize);
		root.setObject(page.getObjList());
		root.setPageCount(page.getCountPage());
		return root;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="getVersion")
	public @ResponseBody Root appStoreEdition(String versions,String appId){
		AppstoreEdition appStoreEdition = appstoreEditionManager.getAppStoreEdition(versions,appId);
		if(appStoreEdition == null){
			root.setMessage(1, "版本号已存在");
		}
		return root;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="checkVersion")
	public @ResponseBody Root checkVersion(String versions,String appId){
		AppstoreEdition appStoreEdition = appstoreEditionManager.checkVersion(versions,appId);
		if(appStoreEdition==null){
			root.setMessage("版本号符合");
		}else{
			root.setMessage(1,appStoreEdition.getVersions());
		}
		return root;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="getMaxVersion")
	public @ResponseBody Root getMaxVersion(String appId){
		AppstoreEdition appStoreEdition = appstoreEditionManager.getMaxVersion(appId);
		root.setObject(appStoreEdition);
		return root;
	}
	
	public StringBuffer plistStr(String iconUrl, String plistUrl, String name, String pkgname) {
		StringBuffer plistString = new StringBuffer();
		plistString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		plistString.append("<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
		plistString.append("<plist version=\"1.0\">");
		plistString.append("<dict>");
		plistString.append("<key>items</key>");
		plistString.append("<array>");
		plistString.append("<dict>");
		plistString.append("<key>assets</key>");
		plistString.append("<array>");
		plistString.append("<dict>");
		plistString.append("<key>kind</key>");
		plistString.append("<string>software-package</string>");
		plistString.append("<key>url</key>");
		plistString.append("<string>" + plistUrl + "</string>");
		plistString.append("</dict>");
		plistString.append("<dict>");
		plistString.append("<key>kind</key>");
		plistString.append("<string>full-size-image</string>");
		plistString.append("<key>needs-shine</key>");
		plistString.append("<true/>");
		plistString.append("<key>url</key>");
		plistString.append("<string>" + iconUrl + "</string>");
		plistString.append("</dict>");
		plistString.append("<dict>");
		plistString.append("<key>kind</key>");
		plistString.append("<string>display-image</string>");
		plistString.append("<key>needs-shine</key>");
		plistString.append("<false/>");
		plistString.append("<key>url</key>");
		plistString.append("<string>" + iconUrl + "</string>");
		plistString.append("</dict>");
		plistString.append("</array>");
		plistString.append("<key>metadata</key>");
		plistString.append("<dict>");
		plistString.append("<key>bundle-identifier</key>");
		plistString.append("<string>" + pkgname + "</string>");
		plistString.append("<key>kind</key>");
		plistString.append("<string>software</string>");
		plistString.append("<key>subtitle</key>");
		plistString.append("<string></string>");
		plistString.append("<key>title</key>");
		plistString.append("<string>" + name + "</string>");
		plistString.append("</dict>");
		plistString.append("</dict>");
		plistString.append("</array>");
		plistString.append("</dict>");
		plistString.append("</plist>");
		return plistString;
	}

}
