package com.nationsky.app.v1;


import java.io.File;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import sun.misc.BASE64Decoder;

import com.nationsky.entity.Root;
import com.nationsky.service.UsersManager;
import com.nationsky.vo.UserVO;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.AES256;
import com.nationsky.webapp.util.AdLogin;
import com.nationsky.webapp.util.Utils;

@Controller
@RequestMapping("/v1/appuser")
@Scope("prototype")
public class AppUsersController extends BaseFormController {
    private UsersManager usersManager = null;

    @Autowired
    public void setUsersManager(UsersManager usersManager) {
        this.usersManager = usersManager;
    }
    
    @Autowired
  	private Root root;

	/**
	 * app客户端登陆
	 */
    @RequestMapping(method=RequestMethod.POST,value="/applogin")
    public @ResponseBody Root login(String userName,String password,String loginType){
    	if(userName != null && password != null && loginType != null){
    		BASE64Decoder k = new BASE64Decoder(); 
    		byte[] key;
    		try {
    			key = AES256.initkey();
    			byte[] data = k.decodeBuffer(password);
    			password = new String(AES256.decrypt(data, key),"UTF-8");
    		} catch (Exception e) {
    			root.setMessage(1, "解密失败");
    		}
    		
    		UserVO user = null;
    		if(loginType.equals("ad")){
    			//AD验证
    			String message = AdLogin.checkMail(userName, password);
    			if(!"验证失败".equals(message)){
    				user = usersManager.findUser(userName,null);
    				root.setMessage("成功");
    				if(user != null){
    					root.setObject(user);
    				}else{
    					root.setMessage(1, "用户名密码错误");
    				}
    			
    			}else{
    				root.setMessage(1, "AD验证失败");
    			}
    		}else if(loginType.equals("mdm")){
    			user = usersManager.findUser(userName,password);
    			if(user != null){
    				root.setMessage("成功");
    				root.setObject(user);
    			}else{
    				root.setMessage(1, "用户名或密码错误");
    			}
    			
    		}
    	}else{
    		root.setMessage(1, "用户名或密码错误");
    	}
    	return root;
    }
	
	
	/**
	 * 用户上传头像
	 * @param id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/headimg")
	public @ResponseBody Root headimg(String userId,HttpServletRequest request){
		ServletContext srevletContext = request.getSession().getServletContext();
		String projectPath = srevletContext.getRealPath("/");
		String projectName = srevletContext.getContextPath();
		projectName = projectName.substring(1, projectName.length());
		String tomcatWebappsPath = projectPath.substring(0, projectPath.indexOf(projectName));
		String serverAddr = Utils.getPropertiesValue("emm_backend_server");
		String emm_backend_static = Utils.getPropertiesValue("emm_backend_static");
		String iconSavePath = File.separatorChar + "headimg" + File.separatorChar;
		String iconpath = "";
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(srevletContext);
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
                        String fileName = file.getOriginalFilename();  
                        String iconRealPath = "";
                        if(fileName.indexOf(".png") != -1 || fileName.indexOf(".jpg") != -1 || fileName.indexOf(".JPG") != -1){
                        	  String zip = fileName.substring(fileName.lastIndexOf('.'),fileName.length());
          					// 保存路径
          					String fileNames = Utils.getUUID() + zip;
          					File filePath = new File(tomcatWebappsPath + emm_backend_static + iconSavePath);
          					if(!filePath.exists()){
          						filePath.mkdir();
          					}
          					iconRealPath = tomcatWebappsPath + emm_backend_static + iconSavePath + fileNames;
          					String iconRomotePath = serverAddr + emm_backend_static + iconSavePath + fileNames;
          					iconRomotePath = iconRomotePath.replaceAll("\\\\", "/");
          					iconpath = iconRomotePath;
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
        usersManager.updateIcon(iconpath, userId);
        root.setMessage(iconpath);
		return root;
	}
}
