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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "code_os", catalog = "emm_backend")
@Indexed
@XmlRootElement
public class CodeOs extends BaseEntity implements Serializable {
	private Long id;
	private String text;
	private String remark;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@DocumentId
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CodeOsVersion> codeOsVersionList = new ArrayList<CodeOsVersion>();

	@OneToMany(mappedBy = "codeOs",fetch=FetchType.EAGER,targetEntity=CodeOsVersion.class,cascade =      //单项一对多配置
        {
        CascadeType.REMOVE
        })
	@Fetch(FetchMode.SELECT)
	@XmlTransient
	@JsonIgnore
	public List<CodeOsVersion> getCodeOsVersionList() {
		return codeOsVersionList;
	}

	public void setCodeOsVersionList(List<CodeOsVersion> codeOsVersionList) {
		this.codeOsVersionList = codeOsVersionList;
	}

	@Column(name = "text")
	@Field
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "remark")
	@Field
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		CodeOs other = (CodeOs) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodeOs [id=" + id + ", text=" + text + ", remark=" + remark
				+ "]";
	}

}
