package com.nationsky.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "app_store", catalog = "emm_backend")
@Indexed
@XmlRootElement
public class AppStore extends BaseEntity implements Serializable {
	private Long id;
	private CodeChannel appsource;
	private String appsourceid;
	private String description;
	private String icon;
	private String iconInfo;
	private Long modelType;
	private String name;
	private String pkgname;
	private String schemesUrl;
	private CodeAppStatus codeAppStatus;
	private String developerName;
	private String developerUrl;
	private String developerLogo;
	private String developerPerson;
	private String developerPhone;
	private String developerDescrpe;
	private AppType appType;
	private CodeOs codeOs;
	private CodeOsStyle codeOsStyle;
	private AppProject appProject;

	private String serviceType; // 应用类型(APP,NATIVE,SEVICE)

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@DocumentId
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private List<AppstoreEdition> appstoreEditions = new ArrayList<AppstoreEdition>();

	@OneToMany(fetch = FetchType.EAGER, targetEntity = AppstoreEdition.class, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, })
	@JoinColumns(value = { @JoinColumn(name = "appStoreId", referencedColumnName = "id", insertable = false, updatable = false) })
	@Fetch(FetchMode.SELECT)
	public List<AppstoreEdition> getAppstoreEditions() {
		return appstoreEditions;
	}

	public void setAppstoreEditions(List<AppstoreEdition> appstoreEditions) {
		this.appstoreEditions = appstoreEditions;
	}

	@ManyToOne
	@JoinColumn(name = "app_source")
	public CodeChannel getAppsource() {
		return this.appsource;
	}

	public void setAppsource(CodeChannel appsource) {
		this.appsource = appsource;
	}

	@ManyToOne
	@JoinColumn(name = "app_project_id")
	public AppProject getAppProject() {
		return appProject;
	}

	public void setAppProject(AppProject appProject) {
		this.appProject = appProject;
	}

	@ManyToOne
	@JoinColumn(name = "app_type")
	public AppType getAppType() {
		return appType;
	}

	public void setAppType(AppType appType) {
		this.appType = appType;
	}
	
	@ManyToOne
	@JoinColumn(name = "code_os_style")
	public CodeOsStyle getCodeOsStyle() {
		return codeOsStyle;
	}

	public void setCodeOsStyle(CodeOsStyle codeOsStyle) {
		this.codeOsStyle = codeOsStyle;
	}

	@ManyToOne
	@JoinColumn(name = "code_os")
	public CodeOs getCodeOs() {
		return codeOs;
	}

	public void setCodeOs(CodeOs codeOs) {
		this.codeOs = codeOs;
	}

	@Column(name = "appsourceid", length = 100)
	@Field
	public String getAppsourceid() {
		return this.appsourceid;
	}

	public void setAppsourceid(String appsourceid) {
		this.appsourceid = appsourceid;
	}

	@Column(name = "description", length = 500)
	@Field
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "icon", length = 200)
	@Field
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "iconInfo", length = 200)
	@Field
	public String getIconInfo() {
		return iconInfo;
	}

	public void setIconInfo(String iconInfo) {
		this.iconInfo = iconInfo;
	}

	@Column(name = "modelType")
	@Field
	public Long getModelType() {
		return this.modelType;
	}

	public void setModelType(Long modelType) {
		this.modelType = modelType;
	}

	@Column(name = "name", length = 200)
	@Field
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pkgname", length = 200)
	@Field
	public String getPkgname() {
		return this.pkgname;
	}

	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}

	@Column(name = "schemesUrl")
	@Field
	public String getSchemesUrl() {
		return this.schemesUrl;
	}

	public void setSchemesUrl(String schemesUrl) {
		this.schemesUrl = schemesUrl;
	}

	@ManyToOne
	@JoinColumn(name = "code_app_status")
	public CodeAppStatus getCodeAppStatus() {
		return codeAppStatus;
	}

	public void setCodeAppStatus(CodeAppStatus codeAppStatus) {
		this.codeAppStatus = codeAppStatus;
	}

	@Column(name = "developerName")
	@Field
	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	@Column(name = "developerUrl")
	@Field
	public String getDeveloperUrl() {
		return developerUrl;
	}

	public void setDeveloperUrl(String developerUrl) {
		this.developerUrl = developerUrl;
	}

	@Column(name = "developerLogo")
	@Field
	public String getDeveloperLogo() {
		return developerLogo;
	}

	public void setDeveloperLogo(String developerLogo) {
		this.developerLogo = developerLogo;
	}

	@Column(name = "developerPerson")
	@Field
	public String getDeveloperPerson() {
		return developerPerson;
	}

	public void setDeveloperPerson(String developerPerson) {
		this.developerPerson = developerPerson;
	}

	@Column(name = "developerPhone")
	@Field
	public String getDeveloperPhone() {
		return developerPhone;
	}

	public void setDeveloperPhone(String developerPhone) {
		this.developerPhone = developerPhone;
	}

	@Column(name = "developerDescrpe")
	@Field
	public String getDeveloperDescrpe() {
		return developerDescrpe;
	}

	public void setDeveloperDescrpe(String developerDescrpe) {
		this.developerDescrpe = developerDescrpe;
	}

	@Column(name = "service_type")
	@Field
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appsource == null) ? 0 : appsource.hashCode());
		result = prime * result + ((appsourceid == null) ? 0 : appsourceid.hashCode());
		result = prime * result + ((appstoreEditions == null) ? 0 : appstoreEditions.hashCode());
		result = prime * result + ((codeAppStatus == null) ? 0 : codeAppStatus.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((developerDescrpe == null) ? 0 : developerDescrpe.hashCode());
		result = prime * result + ((developerLogo == null) ? 0 : developerLogo.hashCode());
		result = prime * result + ((developerName == null) ? 0 : developerName.hashCode());
		result = prime * result + ((developerPerson == null) ? 0 : developerPerson.hashCode());
		result = prime * result + ((developerPhone == null) ? 0 : developerPhone.hashCode());
		result = prime * result + ((developerUrl == null) ? 0 : developerUrl.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modelType == null) ? 0 : modelType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pkgname == null) ? 0 : pkgname.hashCode());
		result = prime * result + ((schemesUrl == null) ? 0 : schemesUrl.hashCode());
		result = prime * result + ((iconInfo == null) ? 0 : iconInfo.hashCode());
		result = prime * result + ((serviceType == null) ? 0 : serviceType.hashCode());
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
		AppStore other = (AppStore) obj;
		if (appsource == null) {
			if (other.appsource != null)
				return false;
		} else if (!appsource.equals(other.appsource))
			return false;
		if (appsourceid == null) {
			if (other.appsourceid != null)
				return false;
		} else if (!appsourceid.equals(other.appsourceid))
			return false;
		if (appstoreEditions == null) {
			if (other.appstoreEditions != null)
				return false;
		} else if (!appstoreEditions.equals(other.appstoreEditions))
			return false;
		if (codeAppStatus == null) {
			if (other.codeAppStatus != null)
				return false;
		} else if (!codeAppStatus.equals(other.codeAppStatus))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (developerDescrpe == null) {
			if (other.developerDescrpe != null)
				return false;
		} else if (!developerDescrpe.equals(other.developerDescrpe))
			return false;
		if (developerLogo == null) {
			if (other.developerLogo != null)
				return false;
		} else if (!developerLogo.equals(other.developerLogo))
			return false;
		if (developerName == null) {
			if (other.developerName != null)
				return false;
		} else if (!developerName.equals(other.developerName))
			return false;
		if (developerPerson == null) {
			if (other.developerPerson != null)
				return false;
		} else if (!developerPerson.equals(other.developerPerson))
			return false;
		if (developerPhone == null) {
			if (other.developerPhone != null)
				return false;
		} else if (!developerPhone.equals(other.developerPhone))
			return false;
		if (developerUrl == null) {
			if (other.developerUrl != null)
				return false;
		} else if (!developerUrl.equals(other.developerUrl))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modelType == null) {
			if (other.modelType != null)
				return false;
		} else if (!modelType.equals(other.modelType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pkgname == null) {
			if (other.pkgname != null)
				return false;
		} else if (!pkgname.equals(other.pkgname))
			return false;
		if (schemesUrl == null) {
			if (other.schemesUrl != null)
				return false;
		} else if (!schemesUrl.equals(other.schemesUrl))
			return false;
		if (iconInfo == null) {
			if (other.iconInfo != null)
				return false;
		} else if (!iconInfo.equals(other.iconInfo))
			if (serviceType == null) {
				if (other.serviceType != null)
					return false;
			} else if (!serviceType.equals(other.serviceType))
				return false;
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
