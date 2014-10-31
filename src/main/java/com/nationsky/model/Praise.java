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
@Table(name="idea_praise",catalog="emm_backend")
@Indexed
@XmlRootElement(name = "praise")
public class Praise extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String userId;
	private Long coolIdeaId;
	private Date createTime;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@DocumentId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "user_id", length = 20)
	@Field
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "cool_idea_id", length = 20)
	@Field
	public Long getCoolIdeaId() {
		return coolIdeaId;
	}

	public void setCoolIdeaId(Long coolIdeaId) {
		this.coolIdeaId = coolIdeaId;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	@Field
	@XmlJavaTypeAdapter(value = DateAdapter.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Praise [id=" + id + ", userId=" + userId + ", coolIdeaId=" + coolIdeaId + ", createTime=" + createTime + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Praise pojo = (Praise) obj;
		if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
		if (userId != null ? !userId.equals(pojo.userId) : pojo.userId != null) return false;
		if (coolIdeaId != null ? !coolIdeaId.equals(pojo.coolIdeaId) : pojo.coolIdeaId != null) return false;
		if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((coolIdeaId == null) ? 0 : coolIdeaId.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		return result;
	}

}
