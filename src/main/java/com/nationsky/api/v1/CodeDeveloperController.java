package com.nationsky.api.v1;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import com.nationsky.model.CodeDeveloper;
import com.nationsky.model.CodeOsVersion;
import com.nationsky.service.CodeDeveloperManager;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;

@Controller
@RequestMapping("/v1/codeDeveloper")
@Scope("prototype")
public class CodeDeveloperController extends BaseFormController {
	@Autowired
	private CodeDeveloperManager codeDeveloperManager = null;

	@Autowired
	private Root root;

	@RequestMapping(value = "/isExistsText",method = RequestMethod.POST)
	public @ResponseBody
	Root isExistsText(CodeDeveloper codeDeveloper) {
		if(codeDeveloperManager.exists(codeDeveloper)){
			root.setCode(-1);
		}
		return root;
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Root getAll() {
		List<CodeDeveloper> codeList = codeDeveloperManager.getAll();
		root.setObject(codeList);
		return root;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Root save(CodeDeveloper codeDeveloper,HttpServletRequest request) {
		// CodeDeveloper developer =
		// codeDeveloperManager.exists(codeDeveloper.getText());
		// if (developer != null) {
		// root.setMessage(1, "此名称已存在");
		// } else {
		ServletContext servletContext = request.getSession().getServletContext();
    	String projectPath = servletContext.getRealPath("/");
		String projectName = servletContext.getContextPath();
		projectName = projectName.substring(1, projectName.length());
		String tomcatWebappsPath = projectPath.substring(0, projectPath.indexOf(projectName));
		String serverAddr = Utils.getPropertiesValue("emm_backend_server");
		String emm_backend_static = Utils.getPropertiesValue("emm_backend_static");
		String iconSavePath = File.separatorChar + "appImages" + File.separatorChar;
		String icon = codeDeveloper.getDeveloperLogo();
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
        }
        codeDeveloper.setDeveloperLogo(icon);
		CodeDeveloper developer = codeDeveloperManager.save(codeDeveloper);
		if (developer == null) {
			root.setMessage(1, "保存失败");
		} else {
			if(codeDeveloper.getId() != null && !"".equals(codeDeveloper.getId())){
  				root.setMessage("修改成功");
  			}else{
  				root.setMessage("添加成功");
  			}
		}
		// }
		return root;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	Root delete(@PathVariable Long id) {
		codeDeveloperManager.remove(id);
		root.setMessage("删除成功");
		return root;
	}

	/**
	 * 查看详细
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody
	Root getById(@PathVariable Long id) {
		CodeDeveloper codeDeveloper = codeDeveloperManager.get(id);
		root.setObject(codeDeveloper);
		return root;
	}
}
