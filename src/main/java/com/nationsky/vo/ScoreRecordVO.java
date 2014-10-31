package com.nationsky.vo;

import java.io.Serializable;

import com.nationsky.entity.BaseEntity;

public class ScoreRecordVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer logCount;		//连续登陆次数
	private Integer totalScore;		//历史总积分
	private Integer usableScore;	//可用积分
	private Integer todayScore;		//今日获得积分
	
	public Integer getLogCount() {
		return logCount;
	}

	public void setLogCount(Integer logCount) {
		this.logCount = logCount;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getUsableScore() {
		return usableScore;
	}

	public void setUsableScore(Integer usableScore) {
		this.usableScore = usableScore;
	}

	public Integer getTodayScore() {
		return todayScore;
	}

	public void setTodayScore(Integer todayScore) {
		this.todayScore = todayScore;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" [");
        sb.append("logCount").append("='").append(getLogCount()).append("', ");
        sb.append("totalScore").append("='").append(getTotalScore()).append("', ");
        sb.append("usableScore").append("='").append(getUsableScore()).append("', ");
        sb.append("todayScore").append("='").append(getTodayScore()).append("'");
        sb.append("]");
        return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreRecordVO pojo = (ScoreRecordVO)o;
        if (logCount != null ? !logCount.equals(pojo.logCount) : pojo.logCount != null) return false;
        if (totalScore != null ? !totalScore.equals(pojo.totalScore) : pojo.totalScore != null) return false;
        if (usableScore != null ? !usableScore.equals(pojo.usableScore) : pojo.usableScore != null) return false;
        if (todayScore != null ? !todayScore.equals(pojo.todayScore) : pojo.todayScore != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
        result = (logCount != null ? logCount.hashCode() : 0);
        result = 31 * result + (totalScore != null ? totalScore.hashCode() : 0);
        result = 31 * result + (usableScore != null ? usableScore.hashCode() : 0);
        result = 31 * result + (todayScore != null ? todayScore.hashCode() : 0);
        return result;
	}

	
}
