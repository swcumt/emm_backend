package com.nationsky.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nationsky.entity.BaseEntity;
import com.nationsky.webapp.util.CustomDateSerializer;
/**
 * app应用详情
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class AppStoreVO extends BaseEntity implements Serializable{
	
	private String appstoreid;
	
	private String createTime;
	
	private String appSize;
	
	private String icon;//应用图标
	
	private String iconInfo;//应用详情图标
	
	private String versionDescription;
	
	private String schemesUrl;
	
	private String appId;//应用ID
	
	private String NAME;//应用名称
	
	private String plistUrl;
	
	private String pkgname;

	private String fullTrialText; //appstoreid
	
	private String version;//应用版本

	private String descriptionText;
	
	private String company;
	
	private String versionId;
	
	private String ipaUrl;
	
	private String full_trial_id;
	
	private String version_type;
	
	private String appsourceid;
	
	private String service_type;
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSchemesUrl() {
		return schemesUrl;
	}

	public void setSchemesUrl(String schemesUrl) {
		this.schemesUrl = schemesUrl;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getPlistUrl() {
		return plistUrl;
	}

	public void setPlistUrl(String plistUrl) {
		this.plistUrl = plistUrl;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFullTrialText() {
		return fullTrialText;
	}

	public void setFullTrialText(String fullTrialText) {
		this.fullTrialText = fullTrialText;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public String getAppstoreid() {
		return appstoreid;
	}

	public void setAppstoreid(String appstoreid) {
		this.appstoreid = appstoreid;
	}

	public String getVersionDescription() {
		return versionDescription;
	}

	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}

	public String getPkgname() {
		return pkgname;
	}

	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getIpaUrl() {
		return ipaUrl;
	}

	public void setIpaUrl(String ipaUrl) {
		this.ipaUrl = ipaUrl;
	}

	public String getFull_trial_id() {
		return full_trial_id;
	}

	public void setFull_trial_id(String full_trial_id) {
		this.full_trial_id = full_trial_id;
	}

	public String getIconInfo() {
		return iconInfo;
	}

	public void setIconInfo(String iconInfo) {
		this.iconInfo = iconInfo;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public String getVersion_type() {
		return version_type;
	}

	public void setVersion_type(String version_type) {
		this.version_type = version_type;
	}

	public String getAppsourceid() {
		return appsourceid;
	}

	public void setAppsourceid(String appsourceid) {
		this.appsourceid = appsourceid;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	
}
