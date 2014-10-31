package com.nationsky.api.v1;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.FrontUser;
import com.nationsky.model.ScoreUseLog;
import com.nationsky.model.Users;
import com.nationsky.service.FrontUserManager;
import com.nationsky.service.ScoreUseLogManager;
import com.nationsky.service.UsersManager;
import com.nationsky.vo.AppScoreVO;
import com.nationsky.vo.ScoreRecordVO;
import com.nationsky.webapp.controller.BaseFormController;
/**
 * 用户积分管理
 * @author techlon
 *
 */
@Controller
@RequestMapping("/v1/scoreUseLog")
@Scope("prototype")
public class ApiScoreUseLogController extends BaseFormController {

	private ScoreUseLogManager scoreUseLogManager = null;
	private FrontUserManager usersManager;
	@Autowired
	public void setScoreUseLogManager(ScoreUseLogManager scoreUseLogManager) {
		this.scoreUseLogManager = scoreUseLogManager;
	}
	@Autowired
	public void setUsersManager(FrontUserManager usersManager) {
		this.usersManager = usersManager;
	}

	@Autowired
    private Root root;
	/**
	 * 用户添加积分记录
	 * @param scoreUseLog
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public @ResponseBody
	Root add(ScoreUseLog scoreUseLog) {
		try{
			scoreUseLog.setUserTime(new Date());
			if(scoreUseLog.getScore() > 0){
				scoreUseLog.setFlag("1");
			}else{
				scoreUseLog.setFlag("0");
			}
			ScoreUseLog scoreUse = scoreUseLogManager.save(scoreUseLog);
			if (scoreUse == null) {
				root.setMessage(1, "添加失败");
			} else {
				FrontUser user = usersManager.get(scoreUseLog.getUserId());
				user.setScore(user.getScore() + scoreUseLog.getScore());
				usersManager.save(user);
				root.setMessage("添加成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
			root.setMessage(1, "添加失败");
		}
		return root;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userId}/{flag}")
	public @ResponseBody
	Root getScoreUserLog(@PathVariable String userId,@PathVariable String flag) {
		List<ScoreUseLog> scoreUseLogList = scoreUseLogManager.getLogByUserId(userId,flag);
		root.setObject(scoreUseLogList);
		return root;
	}
	
	
	
}
