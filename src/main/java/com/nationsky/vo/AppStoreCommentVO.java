package com.nationsky.vo;

import java.io.Serializable;

import com.nationsky.entity.BaseEntity;

@SuppressWarnings("serial")
public class AppStoreCommentVO  extends BaseEntity implements Serializable{

	private String conntext;
	
	private String createTime;
	
	private String score;
	
	private String icon;
	
	private String position;
	
	private String niceName;
	
	public String getConntext() {
		return conntext;
	}

	public void setConntext(String conntext) {
		this.conntext = conntext;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" [");
        sb.append("conntext").append("='").append(getConntext()).append("', ");
        sb.append("createTime").append("='").append(getCreateTime()).append("', ");
        sb.append("score").append("='").append(getScore()).append("', ");
        sb.append("icon").append("='").append(getIcon()).append("', ");
        sb.append("position").append("='").append(getPosition()).append("', ");
        sb.append("niceName").append("='").append(getNiceName()).append("'");
        sb.append("]");
      
        return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppStoreCommentVO pojo = (AppStoreCommentVO)o;
        if (conntext != null ? !conntext.equals(pojo.conntext) : pojo.conntext != null) return false;
        if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;
        if (score != null ? !score.equals(pojo.score) : pojo.score != null) return false;
        if (icon != null ? !icon.equals(pojo.icon) : pojo.icon != null) return false;
        if (position != null ? !position.equals(pojo.position) : pojo.position != null) return false;
        if (niceName != null ? !niceName.equals(pojo.niceName) : pojo.niceName != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		
		int result = 0;
        result = (conntext != null ? conntext.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (niceName != null ? niceName.hashCode() : 0);
        return result;
	}

}
