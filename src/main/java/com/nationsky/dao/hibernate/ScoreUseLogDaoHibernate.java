package com.nationsky.dao.hibernate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.ScoreUseLogDao;
import com.nationsky.model.ScoreUseLog;
import com.nationsky.webapp.util.DateUtil;

@Repository("scoreUseLogDao")
public class ScoreUseLogDaoHibernate extends GenericDaoHibernate<ScoreUseLog, Long> implements ScoreUseLogDao {

	public ScoreUseLogDaoHibernate() {
		super(ScoreUseLog.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreUseLog> getLogByUserId(String userId) {
		Long id = Long.valueOf(userId);
		List<ScoreUseLog> list = getSession().createCriteria(ScoreUseLog.class).add(Restrictions.eq("userId",id)).addOrder(Order.desc("userTime")).list();
		return list;
	}

	@Override
	public List<ScoreUseLog> getLogByUserId(String userId, String flag) {
		Criteria criteria = getSession().createCriteria(ScoreUseLog.class).add(Restrictions.eq("userId",userId));
		if(flag.equals("0") || flag.equals("1")){
			criteria.add(Restrictions.eq("flag",flag));
		}
		List<ScoreUseLog> list = criteria.list();
		return list;
	}
	
	@Override
	public int getTotalScore(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(score) from score_use_log where score > 0");
		Query query = getSession().createSQLQuery(sql.toString());
		BigDecimal uniqueResult = (BigDecimal) query.uniqueResult();
		int score = 0;
		if(uniqueResult != null){
			score = uniqueResult.intValue();
		}
		return score;
	}

	@Override
	public int getEnableScore(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(score) from score_use_log ");
		Query query = getSession().createSQLQuery(sql.toString());
		BigDecimal uniqueResult = (BigDecimal) query.uniqueResult();
		int score = 0;
		if(uniqueResult != null){
			score = uniqueResult.intValue();
		}
		return score;
	}

	@Override
	public int getTodayScore(String userId) {
		StringBuffer sql = new StringBuffer();
		Date date = new Date(DateUtil.getTimesmorning(0));
		String today = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
		sql.append("select sum(score) from score_use_log where userTime > '"+today+"'");
		Query query = getSession().createSQLQuery(sql.toString());
		BigDecimal uniqueResult = (BigDecimal) query.uniqueResult();
		int score = 0;
		if(uniqueResult != null){
			score = uniqueResult.intValue();
		}
		return score;
	}

}
