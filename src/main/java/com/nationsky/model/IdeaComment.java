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

@Entity
@Table(name="idea_comment",catalog="emm_backend")
@Indexed
@XmlRootElement(name = "ideaComment")
public class IdeaComment extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String content;		//评论内容
	private Long coolIdeaId;	//酷点子id
	private Date createTime;	//评论时间
	private FrontUser user;			//评论人
	
	@Id @GeneratedValue(strategy=IDENTITY) @DocumentId 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="content", length = 1000)
    @Field
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="cool_idea_id", length = 1000)
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
	@ManyToOne
	@JoinColumn(name = "user_id")
	public FrontUser getUser() {
		return user;
	}

	public void setUser(FrontUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "IdeaComment [id=" + id + ", content=" + content + ", coolIdeaId=" + coolIdeaId + ", user=" + user + ", createTime=" + createTime + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdeaComment pojo = (IdeaComment) obj;
		if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
		if (content != null ? !content.equals(pojo.content) : pojo.content != null) return false;
		if (coolIdeaId != null ? !coolIdeaId.equals(pojo.coolIdeaId) : pojo.coolIdeaId != null) return false;
		if (user != null ? !user.equals(pojo.user) : pojo.user != null) return false;
		if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((coolIdeaId == null) ? 0 : coolIdeaId.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		return result;
	}

}
