package com.nationsky.api.v1;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.FrontUser;
import com.nationsky.service.FrontUserManager;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.DateUtil;

@Controller
@RequestMapping("/v1/frontUserLogin")
@Scope("prototype")
public class ApiFrontUserLoginController extends BaseFormController  {
	
	private FrontUserManager frontUserManager;
	@Autowired
	public void setFrontUserManager(FrontUserManager frontUserManager) {
		this.frontUserManager = frontUserManager;
	}
	
	@Autowired
  	private Root root;
	
	/**
     * 前端用户登录
     */
	@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Root userLogin(FrontUser user){
		FrontUser flag = frontUserManager.findUsers(user.getLoginName(),user.getPassword());
    	if(flag == null){
    		root.setMessage(1, "用户名或密码错误");
    	}else{
    		Date lastLoginTime = flag.getLastLoginTime();
    		if(lastLoginTime == null){
    			flag.setSeriesLoginCount(1);
    			flag.setFlag(1l);
    		}else{
    			Date dateAgo = DateUtil.getDateAgo(new Date(),-1);
    			boolean sameDate1 = DateUtil.isSameDate(new Date(),lastLoginTime);
    			boolean sameDate2 = DateUtil.isSameDate(dateAgo,lastLoginTime);
    			if(sameDate2){
    				flag.setSeriesLoginCount(flag.getSeriesLoginCount() + 1);
    			}else if(sameDate1){
    			}else{
    				flag.setSeriesLoginCount(1);
    			}
    			flag.setFlag(0l);
    		}
    		flag.setLastLoginTime(new Date());
    		frontUserManager.save(flag);
    		root.setMessage(0,"登录验证成功");
    		root.setObject(flag);
    	}
    	return root;
    }

}
