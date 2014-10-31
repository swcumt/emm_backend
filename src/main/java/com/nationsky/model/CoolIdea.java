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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.adapter.DateAdapter;
import com.nationsky.entity.BaseEntity;

@Entity
@Table(name="cool_idea",catalog="emm_backend")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexed
@XmlRootElement(name = "coolIdea")
public class CoolIdea extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String content;			//点子内容
	private FrontUser users;			//发布人
	private Date createTime;		//发布点子时间
	private Integer commentNumber; 	//评论人数
	private Integer praiseNumber;	//点赞人数
	private Date commentTime;		//最后评论时间
	private Integer flag;			//标志(0:待审阅,1:已采纳,2:已拒绝)
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@DocumentId  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="content", length = 500)
    @Field
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    @ManyToOne
	@JoinColumn(name = "user_id")
	public FrontUser getUsers() {
		return users;
	}

	public void setUsers(FrontUser users) {
		this.users = users;
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
	@Column(name = "comment_number")
	@Field
	public Integer getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "commentTime", length = 19)
	@Field
	@XmlJavaTypeAdapter(value = DateAdapter.class)
	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	@Column(name="praise_number")
    @Field
	public Integer getPraiseNumber() {
		return praiseNumber;
	}

	public void setPraiseNumber(Integer praiseNumber) {
		this.praiseNumber = praiseNumber;
	}
	@Column(name="flag")
    @Field
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "CoolIdea [id=" + id + ", content=" + content + ", users=" + users + ", createTime=" + createTime + ", commentNumber=" + commentNumber + ", commentTime=" + commentTime + ", flag=" + flag + ", praiseNumber=" + praiseNumber +"]";
//		return "";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoolIdea pojo = (CoolIdea) obj;
		if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
		if (content != null ? !content.equals(pojo.content) : pojo.content != null) return false;
		if (users != null ? !users.equals(pojo.users) : pojo.users != null) return false;
		if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;
		if (commentNumber != null ? !commentNumber.equals(pojo.commentNumber) : pojo.commentNumber != null) return false;
		if (commentTime != null ? !commentTime.equals(pojo.commentTime) : pojo.commentTime != null) return false;
		if (flag != null ? !flag.equals(pojo.flag) : pojo.flag != null) return false;
		if (praiseNumber != null ? !praiseNumber.equals(pojo.praiseNumber) : pojo.praiseNumber != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((commentNumber == null) ? 0 : commentNumber.hashCode());
		result = prime * result + ((commentTime == null) ? 0 : commentTime.hashCode());
		result = prime * result + ((praiseNumber == null) ? 0 : praiseNumber.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		return result;
	}

}
