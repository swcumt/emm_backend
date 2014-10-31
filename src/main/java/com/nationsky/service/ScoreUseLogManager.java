package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.ScoreUseLog;
import com.nationsky.vo.ScoreRecordVO;

@WebService
public interface ScoreUseLogManager extends GenericManager<ScoreUseLog, Long> {

	List<ScoreUseLog> getLogByUserId(String userId);

	List<ScoreUseLog> getLogByUserId(String userId, String flag);

	ScoreRecordVO getUserScoreDetail(String userId);

}
