package com.nationsky.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;

@Entity
@Table(name = "code_developer", catalog = "emm_backend")
@Indexed
@XmlRootElement
public class CodeDeveloper extends BaseEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String developerName;
	private String developerUrl;
	private String developerLogo;
	private String developerPerson;
	private String developerPhone;
	private String developerDescrpe;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@DocumentId
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	@Column(name = "developerName")
	@Field
	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
