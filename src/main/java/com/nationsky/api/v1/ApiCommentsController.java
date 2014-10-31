package com.nationsky.api.v1;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.service.AppCommentManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;

/**
 * 应用评论管理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/aCommentsform*")
@Scope("prototype")
public class ApiCommentsController extends BaseFormController {
    private AppCommentManager appCommentManager = null;

    @Autowired
    public void setAppCommentManager(AppCommentManager appCommentManager) {
        this.appCommentManager = appCommentManager;
    }
    
    @Autowired
    private Root root;

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
    public @ResponseBody Root delete(@PathVariable String id){
    	if(!StringUtils.isBlank(id)){
    		try {
    			appCommentManager.remove(id);
    			root.setMessage("删除成功");
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	return root;
    }
    
    /**
     * 列表
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/{id}")
    public @ResponseBody Root list(@PathVariable String id,String pageSize){
    	Page page = appCommentManager.findCommentList(id,pageSize);
    	root.setObject(page.getObjList());
    	root.setPageCount(page.getCountPage());
    	return root;
    }
    
}
