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
@Table(name="app_type",catalog="emm_backend")
@Indexed
@XmlRootElement
public class AppType extends BaseEntity implements Serializable{

	private String id;
	
	private String typeName;
	
	@Id @GeneratedValue(strategy=IDENTITY)  @DocumentId
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	 private  List<AppStore> appstore= new ArrayList<AppStore>();
	    
	    @OneToMany(fetch = FetchType.EAGER,targetEntity = AppStore.class,cascade =      //单项一对多配置
	    {
	    CascadeType.PERSIST,CascadeType.MERGE,
	    })
	    @JoinColumns(value={@JoinColumn(name="app_type",referencedColumnName="id",insertable=false,updatable=true)})
	    @XmlTransient
		@JsonIgnore 
		public List<AppStore> getAppstore() {
			return appstore;
		}

		public void setAppstore(List<AppStore> appstore) {
			this.appstore = appstore;
		}

	 @Column(name="typename", length=50)
     @Field
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
