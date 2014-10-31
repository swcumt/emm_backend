package com.nationsky.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

/**
 * 会话
 * @author techlon
 *
 */
@Entity
@Table(name="conversation",catalog="emm_backend")
@Indexed
@XmlRootElement(name = "conversation")
public class Conversation extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private FrontUser userA;		//用户A
	private FrontUser userB;		//用户B
	private Date createTime;	//最后会话时间
	public String uuid;			//发送方传过来的聊天id
	public Integer noReadCount;	//未读消息数
	@Id @GeneratedValue(strategy=IDENTITY) @DocumentId  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "user_id_a")
	public FrontUser getUserA() {
		return userA;
	}

	public void setUserA(FrontUser userA) {
		this.userA = userA;
	}
	@ManyToOne
	@JoinColumn(name = "user_id_b")
	public FrontUser getUserB() {
		return userB;
	}

	public void setUserB(FrontUser userB) {
		this.userB = userB;
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
		return "Conversation [id=" + id + ", userA=" + userA + ", userB=" + userB + ", createTime=" + createTime + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conversation pojo = (Conversation) obj;
		if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
		if (userA != null ? !userA.equals(pojo.userA) : pojo.userA != null) return false;
		if (userB != null ? !userB.equals(pojo.userB) : pojo.userB != null) return false;
		if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userA == null) ? 0 : userA.hashCode());
		result = prime * result + ((userB == null) ? 0 : userB.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		return result;
	}

}
