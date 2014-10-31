package com.nationsky.vo;

import java.io.Serializable;

import com.nationsky.entity.BaseEntity;

public class ConversationVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String userId1;
	private String userId2;
	private Long updateTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId1() {
		return userId1;
	}

	public void setUserId1(String userId1) {
		this.userId1 = userId1;
	}

	public String getUserId2() {
		return userId2;
	}

	public void setUserId2(String userId2) {
		this.userId2 = userId2;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("userId1").append("='").append(getUserId1()).append("'");
        sb.append("userId2").append("='").append(getUserId2()).append("'");
        sb.append("updateTime").append("='").append(getUpdateTime()).append("'");
        sb.append("]");
      
        return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationVO pojo = (ConversationVO)o;
        if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
        if (userId1 != null ? !userId1.equals(pojo.userId1) : pojo.userId1 != null) return false;
        if (userId2 != null ? !userId2.equals(pojo.userId2) : pojo.userId2 != null) return false;
        if (updateTime != null ? !updateTime.equals(pojo.updateTime) : pojo.updateTime != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
        result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (userId1 != null ? userId1.hashCode() : 0);
        result = 31 * result + (userId2 != null ? userId2.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
	}

}
