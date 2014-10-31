package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.ScoreUseLogDao;
import com.nationsky.model.ScoreUseLog;
import com.nationsky.service.ScoreUseLogManager;
import com.nationsky.vo.ScoreRecordVO;

@Service("scoreUseLogManager")
@WebService(serviceName = "ScoreUseLogService", endpointInterface = "com.nationsky.service.ScoreUseLogManager")
public class ScoreUseLogManagerImpl extends GenericManagerImpl<ScoreUseLog, Long> implements ScoreUseLogManager {

	ScoreUseLogDao scoreUseLogDao;
	@Autowired
	public ScoreUseLogManagerImpl(ScoreUseLogDao scoreUseLogDao) {
		super(scoreUseLogDao);
		this.scoreUseLogDao = scoreUseLogDao;
	}
	@Override
	public List<ScoreUseLog> getLogByUserId(String userId) {
		return scoreUseLogDao.getLogByUserId(userId);
	}
	@Override
	public List<ScoreUseLog> getLogByUserId(String userId, String flag) {
		return scoreUseLogDao.getLogByUserId(userId,flag);
	}
	@Override
	public ScoreRecordVO getUserScoreDetail(String userId) {
		ScoreRecordVO scoreRecordVO = new ScoreRecordVO(); 
		int totalScore = scoreUseLogDao.getTotalScore(userId);
		int usableScore = scoreUseLogDao.getEnableScore(userId);
		int todayScore = scoreUseLogDao.getTodayScore(userId);
		scoreRecordVO.setTodayScore(todayScore);
		scoreRecordVO.setTotalScore(totalScore);
		scoreRecordVO.setUsableScore(usableScore);
		return scoreRecordVO;
	}
	
	
}
