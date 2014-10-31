package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.ScoreUseLog;

/**
 * An interface that provides a data management interface to the ScoreUseLog table.
 */
public interface ScoreUseLogDao extends GenericDao<ScoreUseLog, Long>{

	List<ScoreUseLog> getLogByUserId(String userId);

	List<ScoreUseLog> getLogByUserId(String userId, String flag);

	int getTotalScore(String userId);

	int getEnableScore(String userId);

	int getTodayScore(String userId);

}
