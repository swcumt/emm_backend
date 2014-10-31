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
@Table(name="schedule_list",catalog="emm_backend")
@Indexed
@XmlRootElement
public class ScheduleList extends BaseEntity implements Serializable {
    private Long id;
    private Long scheduleId;
    private String title;
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
        @JoinColumns(value={@JoinColumn(name="schedule_list_id",referencedColumnName="id",insertable=false,updatable=false)})
    @Fetch(FetchMode.SELECT)
    @XmlTransient
	@JsonIgnore
    public List<ScheduleListUser> getScheduleListUsers() {
		return scheduleListUsers;
	}

	public void setScheduleListUsers(List<ScheduleListUser> scheduleListUsers) {
		this.scheduleListUsers = scheduleListUsers;
	}
	
	private Schedule schedule = new Schedule();

	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Schedule.class,cascade =
        {
        CascadeType.REFRESH
        })
	@JoinColumns(value={@JoinColumn(name="schedule_id",referencedColumnName="id",insertable=false,updatable=false)})
	@Fetch(FetchMode.SELECT)
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@Column(name="schedule_id")
    @Field
    public Long getScheduleId() {
        return this.scheduleId;
    }
    
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
    
    @Column(name="title")
    @Field
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
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

        ScheduleList pojo = (ScheduleList) o;

        if (scheduleId != null ? !scheduleId.equals(pojo.scheduleId) : pojo.scheduleId != null) return false;
        if (title != null ? !title.equals(pojo.title) : pojo.title != null) return false;
        if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (scheduleId != null ? scheduleId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("scheduleId").append("='").append(getScheduleId()).append("', ");
        sb.append("title").append("='").append(getTitle()).append("', ");
        sb.append("createTime").append("='").append(getCreateTime()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
