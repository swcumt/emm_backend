package com.nationsky.app.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.Schedule;
import com.nationsky.service.ScheduleManager;
import com.nationsky.webapp.controller.BaseFormController;

/**
 * app服务
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/v1/appschedule*")
@Scope("prototype")
public class AppSchedulesController extends BaseFormController {
    private ScheduleManager scheduleManager = null;

    @Autowired
    public void setScheduleManager(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }

   @Autowired
    private Root root;
   
   /**
    * 服务类列表
    * @return
    */
   @RequestMapping(method=RequestMethod.POST,value="/list")
   public @ResponseBody Root list(){
	   List<Schedule> scheduleList = scheduleManager.getAll();
	   root.setObject(scheduleList);
	   return root;
   }
   
   /**
    * 根据服务项ID查询子服务项列表
    * @param id
    * @return
    */
   @SuppressWarnings("rawtypes")
@RequestMapping(method=RequestMethod.POST,value="/schedulelist")
   public @ResponseBody Root schedilelist( String schedule_id){
		List scheduleList = scheduleManager.findScheduleList(schedule_id);
		root.setObject(scheduleList);
		return root;
   }
   
   /**
    * 根据服务项ID和子服务项ID查询人员列表
    * @param schedule_id
    * @param schedule_list_id
    * @return
    */
   @SuppressWarnings("rawtypes")
@RequestMapping(method=RequestMethod.POST,value="/scheduleuser")
   public @ResponseBody Root scheduleUser(String schedule_id,String schedule_list_id){
	   List scheduleUserList = scheduleManager.findScheduleUser(schedule_id, schedule_list_id);
	   root.setObject(scheduleUserList);
	   return root;
   }
   
}
