package com.nationsky.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;
import com.nationsky.webapp.util.CustomDateSerializer;

@SuppressWarnings("serial")
@Entity
@Table(name = "appstore_edition", catalog = "emm_backend")
@Indexed
@XmlRootElement
public class AppstoreEdition extends BaseEntity implements Serializable {
	private Long id;
	private Long appSize;
	private Long appStoreId;
	private Date createTime;
	private String description;
	private String ipaUrl;
	private String plistUrl;
	private String versions;
	private FullTrialCode fullTrialCode;
//	private CodeOs codeOs;
	private CodeOsVersion codeOsVersion;
	private CodeModel codeModel;
	private List<AppServiceRelation> appServiceRelationList;
	private List<P12> p12List;

	@OneToMany(mappedBy = "appstoreEdition", fetch = FetchType.EAGER, targetEntity = AppServiceRelation.class, cascade = { CascadeType.REMOVE })
	@Fetch(FetchMode.SELECT)
	@XmlTransient
	@JsonIgnore
	public List<AppServiceRelation> getAppServiceRelationList() {
		return appServiceRelationList;
	}

	public void setAppServiceRelationList(List<AppServiceRelation> appServiceRelationList) {
		this.appServiceRelationList = appServiceRelationList;
	}

	@OneToMany(mappedBy = "appstoreEdition", fetch = FetchType.EAGER, targetEntity = P12.class, cascade = { CascadeType.REMOVE })
	@Fetch(FetchMode.SELECT)
	@XmlTransient
	@JsonIgnore
	public List<P12> getP12List() {
		return p12List;
	}

	public void setP12List(List<P12> p12List) {
		this.p12List = p12List;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@DocumentId
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private List<AppComment> commentList = new ArrayList<AppComment>();

	@OneToMany(fetch = FetchType.EAGER, targetEntity = AppComment.class, cascade = // 单项一对多配置
	{ CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, })
	@JoinColumns(value = { @JoinColumn(name = "versionId", referencedColumnName = "id") })
	@XmlTransient
	@JsonIgnore
	public List<AppComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<AppComment> commentList) {
		this.commentList = commentList;
	}

	@Column(name = "appSize")
	@Field
	public Long getAppSize() {
		return this.appSize;
	}

	public void setAppSize(Long appSize) {
		this.appSize = appSize;
	}

	@Column(name = "appStoreId")
	@Field
	public Long getAppStoreId() {
		return this.appStoreId;
	}

	public void setAppStoreId(Long appStoreId) {
		this.appStoreId = appStoreId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	@Field
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "description", length = 500)
	@Field
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "ipaUrl", length = 500)
	@Field
	public String getIpaUrl() {
		return this.ipaUrl;
	}

	public void setIpaUrl(String ipaUrl) {
		this.ipaUrl = ipaUrl;
	}

	@Column(name = "plistUrl", length = 500)
	@Field
	public String getPlistUrl() {
		return this.plistUrl;
	}

	public void setPlistUrl(String plistUrl) {
		this.plistUrl = plistUrl;
	}

	@Column(name = "versions", length = 100)
	@Field
	public String getVersions() {
		return this.versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	@ManyToOne
	@JoinColumn(name = "full_trial_id")
	public FullTrialCode getFullTrialCode() {
		return fullTrialCode;
	}

	public void setFullTrialCode(FullTrialCode fullTrialCode) {
		this.fullTrialCode = fullTrialCode;
	}

	// @ManyToOne
	// @JoinColumn(name="code_os")
	// public CodeOs getCodeOs() {
	// return codeOs;
	// }
	//
	// public void setCodeOs(CodeOs codeOs) {
	// this.codeOs = codeOs;
	// }

	@ManyToOne
	@JoinColumn(name = "code_os_version")
	public CodeOsVersion getCodeOsVersion() {
		return codeOsVersion;
	}

	public void setCodeOsVersion(CodeOsVersion codeOsVersion) {
		this.codeOsVersion = codeOsVersion;
	}

	@ManyToOne
	@JoinColumn(name = "code_model")
	public CodeModel getCodeModel() {
		return codeModel;
	}

	public void setCodeModel(CodeModel codeModel) {
		this.codeModel = codeModel;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AppstoreEdition pojo = (AppstoreEdition) o;

		if (appSize != null ? !appSize.equals(pojo.appSize) : pojo.appSize != null)
			return false;
		if (appStoreId != null ? !appStoreId.equals(pojo.appStoreId) : pojo.appStoreId != null)
			return false;
		if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null)
			return false;
		if (description != null ? !description.equals(pojo.description) : pojo.description != null)
			return false;
		if (ipaUrl != null ? !ipaUrl.equals(pojo.ipaUrl) : pojo.ipaUrl != null)
			return false;
		if (plistUrl != null ? !plistUrl.equals(pojo.plistUrl) : pojo.plistUrl != null)
			return false;
		if (versions != null ? !versions.equals(pojo.versions) : pojo.versions != null)
			return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (appSize != null ? appSize.hashCode() : 0);
		result = 31 * result + (appStoreId != null ? appStoreId.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (ipaUrl != null ? ipaUrl.hashCode() : 0);
		result = 31 * result + (plistUrl != null ? plistUrl.hashCode() : 0);
		result = 31 * result + (versions != null ? versions.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "AppstoreEdition [id=" + id + ", appSize=" + appSize + ", appStoreId=" + appStoreId + ", createTime=" + createTime + ", description=" + description + ", ipaUrl=" + ipaUrl
				+ ", plistUrl=" + plistUrl + ", versions=" + versions + ",fullTrialCode=" + fullTrialCode + ", codeOsVersion=" + codeOsVersion + ", codeModel="
				+ codeModel + ", appServiceRelationList=" + appServiceRelationList + ", p12List=" + p12List + ", commentList=" + commentList + "]";
	}


}
