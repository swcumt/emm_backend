package com.nationsky.api.v1;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.nationsky.model.Users;
import com.nationsky.service.MdmUsersManager;
import com.nationsky.service.UsersManager;
import com.nationsky.utils.ExcelReader;
import com.nationsky.vo.MdmUser;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;

/**
 * mdm用户管理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/mdmuseradmin*")
@Scope("prototype")
public class ApiMdmUserController extends BaseFormController {
    private MdmUsersManager mdmUsersManager = null;
    
    private UsersManager usersManager = null;

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
     * mdm 用户列表
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "mdmlist")
    public @ResponseBody Root mdmUserList(String type,String groupId,String userName,String pageSize,String searchType){
    	try {
		    Page page = mdmUsersManager.findMdmUserList(type, groupId, userName,pageSize,searchType);
    		root.setObject(page.getObjList());
			root.setPageCount(page.getCountPage());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return root;
    }
    
    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "{userId}")
    public @ResponseBody Root deleteMdmUser(@PathVariable String userId){
    	try {
    		MdmUser mdmUser = mdmUsersManager.mdmUserDetail(userId);
			mdmUsersManager.deleteMdmUser(userId);
			//更改应用服务中心后台用户状态
			Users user = usersManager.getUsersByUserName(mdmUser.getLoginId());
			if(user != null){
				user.setLoginFlag(0);
				usersManager.save(user);
			}
			root.setMessage("删除成功!");
		} catch (Exception e) {
				e.printStackTrace();
			root.setMessage(1, "删除失败");
		}
    	return root;
    }
    
    /**
     * 用户详细信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public @ResponseBody Root mdmUserDetail(@PathVariable String id){
    	MdmUser mdmUser = mdmUsersManager.mdmUserDetail(id);
    	root.setObject(mdmUser);
    	return root;
    }
    
    /**
     * 添加修改用户
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "adduser")
    public @ResponseBody Root mdmUserInertOrUpdate(MdmUser mdmUser){
    	try {
    		String result = mdmUsersManager.insertOrUpdateUser(mdmUser);
    		root.setMessage(result);
		} catch (Exception e) {
			root.setMessage(1, "操作失败");
		}
    	return root;
    }
    
    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "modifypassword")
    public @ResponseBody Root updatePassword(String id,String password){
    	String result = "";
    	try {
    		result = mdmUsersManager.updatePassword(id, password);
        	root.setMessage(result);
		} catch (Exception e) {
			root.setMessage(1, result);
		}
    	return root;
    }
    
    /**
     * 修改用户类别
     * @param id
     * @param datatype
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="modifytype")
    public @ResponseBody Root updateUserType(String id,String datatype){
    	String result = "";
    	try {
    		result = mdmUsersManager.updateUserType(id, datatype);
        	root.setMessage(result);
		} catch (Exception e) {
			root.setMessage(1, result);
		}
    	return root;
    }
    
    /**
     * 查询用户组
     * @return
     * @throws Exception 
     */
    @RequestMapping(method=RequestMethod.POST,value="grouplist")
    public @ResponseBody Root groupList() throws Exception{
    	List<Map<String,Object>> groupList = mdmUsersManager.groupList();
    	root.setObject(groupList);
    	return root;
    }
    
    /**
     * 导入用户
     * @return
     */
    @SuppressWarnings("static-access")
	@RequestMapping(method = RequestMethod.POST,value = "exportUser")
    public @ResponseBody Root exportUser(HttpServletRequest request){
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
                        	String zip = originalFileName.substring(originalFileName.lastIndexOf('.'), originalFileName.length());
    						String cname = Utils.getUUID()+zip;
    						String path = request.getSession().getServletContext().getRealPath("/") + "/download/" + cname;
                            File localFile = new File(path);  
	                        try {
								file.transferTo(localFile);
								 InputStream is = new FileInputStream(path);
								 //读取excel数据
						         ExcelReader excelReader = new ExcelReader();
						         Map<Integer, String> map = excelReader.readExcelContent(is);
						         mdmUsersManager.insertData(map);
//						         excelReader.insertData(map);
						         localFile.delete();
						         root.setMessage("导入成功");
							} catch (Exception e) {
								 root.setError("文件上传出错");
							} 
                    }  
                } 
            }
        }
    	
    	return root;
    }
}
