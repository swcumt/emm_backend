package com.nationsky.vo;

import java.io.Serializable;

import com.nationsky.entity.BaseEntity;

@SuppressWarnings("serial")
public class AppScoreVO   extends BaseEntity implements Serializable{

	private Integer score;
	private String appScore;
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getAppScore() {
		return appScore;
	}
	public void setAppScore(String appScore) {
		this.appScore = appScore;
	}
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" [");
        sb.append("score").append("='").append(getScore()).append("', ");
        sb.append("appScore").append("='").append(getAppScore()).append("'");
        sb.append("]");
      
        return sb.toString();
	}
	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppScoreVO pojo = (AppScoreVO)o;
        if (score != null ? !score.equals(pojo.score) : pojo.score != null) return false;
        if (appScore != null ? !appScore.equals(pojo.appScore) : pojo.appScore != null) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
        result = (score != null ? score.hashCode() : 0);
        result = 31 * result + (appScore != null ? appScore.hashCode() : 0);
        return result;
	}
	
}
