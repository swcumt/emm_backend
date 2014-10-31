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
@Table(name = "push_device_token", catalog = "emm_backend")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexed
@XmlRootElement(name = "deviceToken")
public class DeviceToken extends BaseEntity implements Serializable {
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private AppstoreEdition appstoreEdition;
	private String deviceToken;
	private Date createTime;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@DocumentId
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "appstore_edition_id")
	public AppstoreEdition getAppstoreEdition() {
		return appstoreEdition;
	}

	public void setAppstoreEdition(AppstoreEdition appstoreEdition) {
		this.appstoreEdition = appstoreEdition;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appstoreEdition == null) ? 0 : appstoreEdition.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((deviceToken == null) ? 0 : deviceToken.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceToken other = (DeviceToken) obj;
		if (appstoreEdition == null) {
			if (other.appstoreEdition != null)
				return false;
		} else if (!appstoreEdition.equals(other.appstoreEdition))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (deviceToken == null) {
			if (other.deviceToken != null)
				return false;
		} else if (!deviceToken.equals(other.deviceToken))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeviceToken [id=" + id + ", appstoreEdition=" + appstoreEdition + ", deviceToken=" + deviceToken + ", createTime=" + createTime + "]";
	}

}
