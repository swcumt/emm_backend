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
@Table(name = "app_service", catalog = "emm_backend")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexed
@XmlRootElement(name = "appServiceRelation")
public class AppServiceRelation extends BaseEntity implements Serializable {
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private AppstoreEdition appstoreEdition;
	private Service service;
	private Date createTime;
	private String config;
	private Integer setted;

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

	@ManyToOne
	@JoinColumn(name = "server_id")
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
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

	@Column(name = "config", length = 1000)
	@Field
	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	@Column(name = "setted", length = 11)
	@Field
	public Integer getSetted() {
		return setted;
	}

	public void setSetted(Integer setted) {
		this.setted = setted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appstoreEdition == null) ? 0 : appstoreEdition.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
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
		AppServiceRelation other = (AppServiceRelation) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppServiceRelation [id=" + id + ", appstoreEdition=" + appstoreEdition + ", service=" + service + ", createTime=" + createTime + "]";
	}

}
