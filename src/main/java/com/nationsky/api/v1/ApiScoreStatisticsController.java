package com.nationsky.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.FrontUser;
import com.nationsky.service.FrontUserManager;
import com.nationsky.service.ScoreUseLogManager;
import com.nationsky.vo.ScoreRecordVO;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/scoreStatistics")
@Scope("prototype")
public class ApiScoreStatisticsController extends BaseFormController {

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
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userid}")
	public @ResponseBody
	Root getScoreDetail(@PathVariable String userid) { 
		FrontUser frontUser = usersManager.get(userid);
		ScoreRecordVO scoreVO = scoreUseLogManager.getUserScoreDetail(userid);
		scoreVO.setLogCount(frontUser.getSeriesLoginCount() == null ? 0 : frontUser.getSeriesLoginCount());
		root.setObject(scoreVO);
		return root;
	}
	
}
