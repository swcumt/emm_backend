package com.nationsky.api.v1;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.ScheduleList;
import com.nationsky.service.ScheduleListManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;

/**
 * 服务项子类管理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/scheduleListform*")
@Scope("prototype")
public class ApiV1ScheduleListController extends BaseFormController {
    private ScheduleListManager scheduleListManager = null;

    @Autowired
    public void setScheduleListManager(ScheduleListManager scheduleListManager) {
        this.scheduleListManager = scheduleListManager;
    }

    @Autowired
    private Root root;
    
    /**
     * 添加子服务项
     * @param scheduleList
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Root add(ScheduleList schedule){
    	schedule.setCreateTime(new Date());
    	schedule.setSchedule(null);
    	try {
    		ScheduleList scheduleList = scheduleListManager.save(schedule);
        	if(scheduleList == null){
        		root.setMessage(1,"添加失败");
        	}else{
        		if(schedule.getId() != null && !"".equals(schedule.getId())){
      				root.setMessage("修改成功");
      			}else{
      				root.setMessage("添加成功");
      			}
        	}
		} catch (Exception e) {
			root.setMessage(1,"添加失败");
		}
    	
    	return root;
    }
    
    /**
     * 子服务项详细信息
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/{id}")
    public @ResponseBody Root detail(@PathVariable String id){
    	ScheduleList scheduleList = null;
    	if(!StringUtils.isBlank(id)){
    		try {
    			scheduleList = scheduleListManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	root.setObject(scheduleList);
    	return root;
    }
    
    /**
     * 自服务项列表
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/list")
    public @ResponseBody Root list(String pageSize){
    	Page page = scheduleListManager.findScheduleList(pageSize);
    	root.setObject(page.getObjList());
    	root.setPageCount(page.getCountPage());
    	return root;
    }
    
    /**
     * 删除自服务项
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
    public @ResponseBody Root delete(@PathVariable String id){
    	if(!StringUtils.isBlank(id)){
    		try {
    			scheduleListManager.remove(new Long(id));
    			root.setMessage("删除成功");
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	return root;
    }
    
    @RequestMapping(method=RequestMethod.POST,value="/schedule/{id}")
    public @ResponseBody Root scheduleDetail(@PathVariable String id){
    	List<ScheduleList> scheduleList = null;
    	if(!StringUtils.isBlank(id)){
    		try {
    			scheduleList = scheduleListManager.findShceduleList(id);
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	root.setObject(scheduleList);
    	return root;
    }
}
