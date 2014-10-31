package com.nationsky.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nationsky.entity.BaseEntity;

@SuppressWarnings("serial")
public class CommentVO   extends BaseEntity implements Serializable{

	private String countScore;
	
	private Integer count;
	
	@SuppressWarnings("rawtypes")
	private List score = new ArrayList();
	
	@SuppressWarnings("rawtypes")
	private List commentList = new ArrayList();

	public String getCountScore() {
		return countScore;
	}

	public void setCountScore(String countScore) {
		this.countScore = countScore;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@SuppressWarnings("rawtypes")
	public List getScore() {
		return score;
	}

	@SuppressWarnings("rawtypes")
	public void setScore(List score) {
		this.score = score;
	}

	@SuppressWarnings("rawtypes")
	public List getCommentList() {
		return commentList;
	}

	@SuppressWarnings("rawtypes")
	public void setCommentList(List commentList) {
		this.commentList = commentList;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" [");
        sb.append("countScore").append("='").append(getCountScore()).append("', ");
        sb.append("count").append("='").append(getCount()).append("', ");
        sb.append("score").append("='").append(getScore()).append("', ");
        sb.append("commentList").append("='").append(getCommentList()).append("'");
        sb.append("]");
      
        return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentVO pojo = (CommentVO)o;
        if (countScore != null ? !countScore.equals(pojo.countScore) : pojo.countScore != null) return false;
        if (count != null ? !count.equals(pojo.count) : pojo.count != null) return false;
        if (score != null ? !score.equals(pojo.score) : pojo.score != null) return false;
        if (commentList != null ? !commentList.equals(pojo.commentList) : pojo.commentList != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		
		int result = 0;
        result = (countScore != null ? countScore.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (commentList != null ? commentList.hashCode() : 0);
        return result;
	}
	
}
