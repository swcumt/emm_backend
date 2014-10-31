package com.nationsky.api.v1;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.ScheduleListUser;
import com.nationsky.service.ScheduleListUserManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
/**
 * 排班管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/v1/scheduleListUserform*")
@Scope("prototype")
public class ApiScheduleListUserController extends BaseFormController {
    private ScheduleListUserManager scheduleListUserManager = null;

    @Autowired
    public void setScheduleListUserManager(ScheduleListUserManager scheduleListUserManager) {
        this.scheduleListUserManager = scheduleListUserManager;
    }

    @Autowired
    private Root root;
    
    /**
     * 添加排班
     * @param scheduleListUser
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Root add(ScheduleListUser scheduleListUser){
    	scheduleListUser.setCreateTime(new Date());
    	scheduleListUser.setSchedule(null);
    	scheduleListUser.setScheduleList(null);
    	scheduleListUser.setScheduleUser(null);
    	ScheduleListUser listUser = scheduleListUserManager.save(scheduleListUser);
    	if(listUser == null){
    		root.setMessage(1, "添加失败");
    	}else{
    		if(scheduleListUser.getId() != null && !"".equals(scheduleListUser.getId())){
				root.setMessage("修改成功");
			}else{
				root.setMessage("添加成功");
			}
    	}
    	return root;
    }
    
    /**
     * 排班详细
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/{id}")
    public @ResponseBody Root detail(@PathVariable String id){
    	ScheduleListUser listUser = null;
    	//判断参数id是否为空
    	if(!StringUtils.isBlank(id)){
    		try {
    			listUser = scheduleListUserManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	root.setObject(listUser);
    	return root;
    }
    
    /**
     * 删除排班
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
    public @ResponseBody Root delete(@PathVariable String id){
    	if(!StringUtils.isBlank(id)){
    		try {
    			scheduleListUserManager.remove(new Long(id));
    			root.setMessage("删除成功");
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	return root;
    }
    
    /**
     * 排班列表
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/list")
    public @ResponseBody Root list(String pageSize){
        Page page = scheduleListUserManager.findScheduleListUser(pageSize);
    	root.setObject(page.getObjList());
        root.setPageCount(page.getCountPage());
    	return root;
    }
    
}
