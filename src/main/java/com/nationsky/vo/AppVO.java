package com.nationsky.vo;

import java.io.Serializable;

import com.nationsky.entity.BaseEntity;


@SuppressWarnings("serial")
public class AppVO  extends BaseEntity implements Serializable {
	
	private String bundleId;//应用标识
	
	private String trackName;//应用名称
	
	private String sellerName;//开发商名称
	
	private String artworkUrl60;//应用图标
	
	private String description;//应用描述

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getArtworkUrl60() {
		return artworkUrl60;
	}

	public void setArtworkUrl60(String artworkUrl60) {
		this.artworkUrl60 = artworkUrl60;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" [");
        sb.append("trackName").append("='").append(getBundleId()).append("', ");
        sb.append("trackName").append("='").append(getTrackName()).append("', ");
        sb.append("sellerName").append("='").append(getSellerName()).append("', ");
        sb.append("artworkUrl60").append("='").append(getArtworkUrl60()).append("', ");
        sb.append("description").append("='").append(getDescription()).append("'");
        sb.append("]");
      
        return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppVO pojo = (AppVO)o;
        if (bundleId != null ? !bundleId.equals(pojo.bundleId) : pojo.bundleId != null) return false;
        if (trackName != null ? !trackName.equals(pojo.trackName) : pojo.trackName != null) return false;
        if (sellerName != null ? !sellerName.equals(pojo.sellerName) : pojo.sellerName != null) return false;
        if (artworkUrl60 != null ? !artworkUrl60.equals(pojo.artworkUrl60) : pojo.artworkUrl60 != null) return false;
        if (description != null ? !description.equals(pojo.description) : pojo.description != null) return false;
        
		return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
        result = (bundleId != null ? bundleId.hashCode() : 0);
        result = 31 * result + (trackName != null ? trackName.hashCode() : 0);
        result = 31 * result + (sellerName != null ? sellerName.hashCode() : 0);
        result = 31 * result + (artworkUrl60 != null ? artworkUrl60.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
	}

}
