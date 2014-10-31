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
 * 聊天记录
 * 
 */
@Entity
@Table(name="chat_record",catalog="emm_backend")
@Indexed
@XmlRootElement(name = "chatRecord")
public class ChatRecord extends BaseEntity implements Serializable {  

	private static final long serialVersionUID = 1L;
	private Long id;
	private String record;
	private Date createTime;
	private Long conversationId;
	private FrontUser users;
	private String uuid;
	private String isRead;	//是否已读(0:未读,1:已读)
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@DocumentId 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "record", length = 20)
	@Field
	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
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
	@Column(name = "conversation_id", length = 20)
	@Field
	public Long getConversationId() {
		return conversationId;
	}

	public void setConversationId(Long conversationId) {
		this.conversationId = conversationId;
	}
	@ManyToOne
	@JoinColumn(name = "from_user_id")
	public FrontUser getUsers() {
		return users;
	}

	public void setUsers(FrontUser users) {
		this.users = users;
	}
	@Column(name = "uuid", length = 32)
	@Field
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Column(name = "isRead", length = 32)
	@Field
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "Praise [id=" + id + ", users=" + users + ", record=" + record + ", createTime=" + createTime + ", conversationId=" + conversationId + ", uuid=" + uuid + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatRecord pojo = (ChatRecord) obj;
		if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
		if (users != null ? !users.equals(pojo.users) : pojo.users != null) return false;
		if (record != null ? !record.equals(pojo.record) : pojo.record != null) return false;
		if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;
		if (conversationId != null ? !conversationId.equals(pojo.conversationId) : pojo.conversationId != null) return false;
		if (uuid != null ? !uuid.equals(pojo.uuid) : pojo.uuid != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		result = prime * result + ((record == null) ? 0 : record.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((conversationId == null) ? 0 : conversationId.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

}
