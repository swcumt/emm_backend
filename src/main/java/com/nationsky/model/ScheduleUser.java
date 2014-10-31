package com.nationsky.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;
import com.nationsky.webapp.util.CustomDateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name="schedule_user",catalog="emm_backend")
@Indexed
@XmlRootElement
public class ScheduleUser extends BaseEntity implements Serializable {
    private Long id;
    private String name;
    private String userIcon;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private Date createTime;

    @Id @GeneratedValue(strategy=IDENTITY) @DocumentId    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    private List<ScheduleListUser> scheduleListUsers = new ArrayList<ScheduleListUser>();
    @OneToMany(fetch = FetchType.EAGER,targetEntity = ScheduleListUser.class,cascade =      //单项一对多配置
        {
        CascadeType.REMOVE
        })
        @JoinColumns(value={@JoinColumn(name="schedule_user_id",referencedColumnName="id",insertable=false,updatable=false)})
    @Fetch(FetchMode.SELECT)
    @XmlTransient
	@JsonIgnore    
    public List<ScheduleListUser> getScheduleListUsers() {
		return scheduleListUsers;
	}

	public void setScheduleListUsers(List<ScheduleListUser> scheduleListUsers) {
		this.scheduleListUsers = scheduleListUsers;
	}
    
    @Column(name="name")
    @Field
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="phone_number", length=50)
    @Field
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Column(name="userIcon", length=200)
    @Field
    public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	@Column(name="mobile_phone_number", length=50)
    @Field
    public String getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }
    
    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", length=19)
    @Field
    @JsonSerialize(using = CustomDateSerializer. class )
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleUser pojo = (ScheduleUser) o;

        if (name != null ? !name.equals(pojo.name) : pojo.name != null) return false;
        if (userIcon != null ? !userIcon.equals(pojo.userIcon) : pojo.userIcon != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(pojo.phoneNumber) : pojo.phoneNumber != null) return false;
        if (mobilePhoneNumber != null ? !mobilePhoneNumber.equals(pojo.mobilePhoneNumber) : pojo.mobilePhoneNumber != null) return false;
        if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (name != null ? name.hashCode() : 0);
        result = (userIcon != null ? userIcon.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (mobilePhoneNumber != null ? mobilePhoneNumber.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("name").append("='").append(getName()).append("', ");
        sb.append("userIcon").append("='").append(getUserIcon()).append("', ");
        sb.append("phoneNumber").append("='").append(getPhoneNumber()).append("', ");
        sb.append("mobilePhoneNumber").append("='").append(getMobilePhoneNumber()).append("', ");
        sb.append("createTime").append("='").append(getCreateTime()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
