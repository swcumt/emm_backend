package com.nationsky.api.v1;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

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
import com.nationsky.model.Version;
import com.nationsky.service.VersionManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;

/**
 * 应用版本管理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/versionsform*")
@Scope("prototype")
public class ApiVersionsController extends BaseFormController {
    private VersionManager versionManager = null;

    @Autowired
    public void setVersionManager(VersionManager versionManager) {
        this.versionManager = versionManager;
    }
    
    @Autowired
  	private Root root;
    
    @SuppressWarnings("deprecation")
	@RequestMapping(method=RequestMethod.POST,value="/add")
    public @ResponseBody Root add(Version version,HttpServletRequest request){
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    	//判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
        	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
            	String path = request.getRealPath("/");
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        if(fileName.indexOf(".png") !=-1 || fileName.indexOf(".jpg") != -1 || fileName.indexOf(".JPG") != -1){
                        	path += "/download/images/AppCenter-icon-120.png";  
                        }else if(fileName.indexOf(".ipa")!=-1){
                        	path += "/download/AppCenter.ipa";  
                    	}
                        File localFile = new File(path);  
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
        if(version.getId() != null && !"".equals(version.getId())){
				root.setMessage("修改成功");
			}else{
				root.setMessage("添加成功");
			}
    	version.setCreateTime(new Date());
    	versionManager.save(version);
    	return root;
    }
    
    /**
     * 版本列表
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/list")
    public @ResponseBody Root list(String pageSize){
    	Page page  = versionManager.getVersionList(pageSize);
    	root.setObject(page.getObjList());
    	root.setPageCount(page.getCountPage());
    	return root;
    }

    /**
     * 详细
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/{id}")
    public @ResponseBody Root detail(@PathVariable String id){
    	Version version = null;
    	if(!StringUtils.isBlank(id)){
    		try {
    			version = versionManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	root.setObject(version);
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
				versionManager.remove(new Long(id));
				 root.setMessage(0, "删除成功");
			} catch (Exception e) {
				 root.setError("数据不存在");
			}
    	}
    	return root;
    }
}
