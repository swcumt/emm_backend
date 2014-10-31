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

/**
 * 设备表
 * @author xiaohuiyang
 *
 */
@Entity
@Table(name="vendordevice",catalog="emm_backend")
@Indexed
@XmlRootElement
public class VendorDevice extends BaseEntity implements Serializable{
  
	private static final long serialVersionUID = 1L;
	private Long id;
	private String deviceid;		//设备id
	private String vendorid;		//用户id
	private String password;		//密码
	private String type;			//类型(IOS,Andriode)
	
	@Id @GeneratedValue(strategy=IDENTITY) @DocumentId 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="deviceid")
    @Field
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	@Column(name="vendorid")
    @Field
	public String getVendorid() {
		return vendorid;
	}
	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}
	@Column(name="password")
    @Field
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="type")
    @Field
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("deviceid").append("='").append(getDeviceid()).append("', ");
        sb.append("vendorid").append("='").append(getVendorid()).append("', ");
        sb.append("password").append("='").append(getPassword()).append("', ");
        sb.append("type").append("='").append(getType()).append("'");
        sb.append("]");
      
        return sb.toString();
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VendorDevice pojo = (VendorDevice) o;

        if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
        if (deviceid != null ? !deviceid.equals(pojo.deviceid) : pojo.deviceid != null) return false;
        if (vendorid != null ? !vendorid.equals(pojo.vendorid) : pojo.vendorid != null) return false;
        if (password != null ? !password.equals(pojo.password) : pojo.password != null) return false;
        if (type != null ? !type.equals(pojo.type) : pojo.type != null) return false;
		return true;
	}
	@Override
	public int hashCode() {
		 int result = 0;
	        result = (id != null ? id.hashCode() : 0);
	        result = 31 * result + (deviceid != null ? deviceid.hashCode() : 0);
	        result = 31 * result + (vendorid != null ? vendorid.hashCode() : 0);
	        result = 31 * result + (password != null ? password.hashCode() : 0);
	        result = 31 * result + (type != null ? type.hashCode() : 0);

	        return result;
	}
}
