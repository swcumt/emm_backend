package com.nationsky.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.adapter.DateAdapter;
import com.nationsky.entity.BaseEntity;

@Entity
@Table(name="score_use_log",catalog="emm_backend")
@Indexed
@XmlRootElement
public class ScoreUseLog extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String userId;
	private Integer score;
	private Date userTime;
	private String reason;
	private String flag;		//1:得分,0:消费
	@Id @GeneratedValue(strategy=IDENTITY) @DocumentId 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="user_id")
    @Field
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name="score")
    @Field
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "userTime", length = 19)
	@Field
	@XmlJavaTypeAdapter(value = DateAdapter.class)
	public Date getUserTime() {
		return userTime;
	}

	public void setUserTime(Date userTime) {
		this.userTime = userTime;
	}
	@Column(name="reason" ,length = 200)
    @Field
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	@Column(name="flag" ,length = 2)
    @Field
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "ScoreUseLog [id=" + id + ", userId=" + userId + ", score=" + score + ", userTime=" + userTime + ", reason=" + reason + ", flag=" + flag + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreUseLog pojo = (ScoreUseLog) obj;
		if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
		if (userId != null ? !userId.equals(pojo.userId) : pojo.userId != null) return false;
		if (score != null ? !score.equals(pojo.score) : pojo.score != null) return false;
		if (userTime != null ? !userTime.equals(pojo.userTime) : pojo.userTime != null) return false;
		if (reason != null ? !reason.equals(pojo.reason) : pojo.reason != null) return false;
		if (flag != null ? !flag.equals(pojo.flag) : pojo.flag != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((userTime == null) ? 0 : userTime.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		return result;
	}

}
