package com.nationsky.model;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name="users",catalog="emm_backend")
@Indexed
@XmlRootElement
public class Users extends BaseEntity implements Serializable {
    private Long id;
    private Long flag;
    private String name;
    private String password;
    private String username;
    private Integer loginFlag;

    @Id @GeneratedValue(strategy=IDENTITY) @DocumentId    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="flag")
    @Field
    public Long getFlag() {
        return this.flag;
    }
    
    public void setFlag(Long flag) {
        this.flag = flag;
    }
    
    @Column(name="name")
    @Field
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="password")
    @Field
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="username")
    @Field
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="loginFlag")
    @Field
    public Integer getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Integer loginFlag) {
		this.loginFlag = loginFlag;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users pojo = (Users) o;

        if (flag != null ? !flag.equals(pojo.flag) : pojo.flag != null) return false;
        if (name != null ? !name.equals(pojo.name) : pojo.name != null) return false;
        if (password != null ? !password.equals(pojo.password) : pojo.password != null) return false;
        if (username != null ? !username.equals(pojo.username) : pojo.username != null) return false;
        if (loginFlag != null ? !loginFlag.equals(pojo.loginFlag) : pojo.loginFlag != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (flag != null ? flag.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (loginFlag != null ? loginFlag.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("flag").append("='").append(getFlag()).append("', ");
        sb.append("name").append("='").append(getName()).append("', ");
        sb.append("password").append("='").append(getPassword()).append("', ");
        sb.append("loginFlag").append("='").append(getLoginFlag()).append("', ");
        sb.append("username").append("='").append(getUsername()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
