package com.nationsky.api.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.AppType;
import com.nationsky.service.AppTypeManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;

/**
 * 应用分类管理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/apptype*")
@Scope("prototype")
public class ApiAppTypeController extends BaseFormController {
    private AppTypeManager appTypeManager = null;

    @Autowired
    public void setAppTypeManager(AppTypeManager appTypeManager) {
        this.appTypeManager = appTypeManager;
    }
    
    @Autowired
  	private Root root;
    
    /**
     * 应用分类列表
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="list")
    public @ResponseBody Root appTypeList(String pageSize){
    	Page page = appTypeManager.findAppType(pageSize);
    	root.setObject(page.getObjList());
    	root.setPageCount(page.getCountPage());
    	return root;
    }
    
    /**
     * 添加应用分类
     * @param appType
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Root addAppType(AppType appType){
    	try {
    		AppType appTypeVo = appTypeManager.save(appType);
        	if(appTypeVo != null){
        		if(appType.getId() != null && !"".equals(appType.getId())){
    				root.setMessage("修改成功");
    			}else{
    				root.setMessage("添加成功");
    			}
        	}else{
        		root.setMessage(1,"添加失败");
        	}
		} catch (Exception e) {
			root.setMessage(1, "添加失败");
		}
    	
    	return root;
    }
    
    /**
     * 删除应用分类
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
    public @ResponseBody Root deleteAppType(@PathVariable String id){
    	try {
			appTypeManager.remove(id);
			root.setMessage("删除成功");
		} catch (Exception e) {
			root.setError("删除失败");
		}
		return root;
	}
    
    /**
     * 应用分类详细
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value="{id}")
    public @ResponseBody Root appTypeDetail(@PathVariable String id){
    	try {
    		AppType appType = appTypeManager.get(id);
    		root.setObject(appType);
		} catch (Exception e) {
			root.setMessage(1, "请求数据异常");
		}
    	return root;
    }
}
