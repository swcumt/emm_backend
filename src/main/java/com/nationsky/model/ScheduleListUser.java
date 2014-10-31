package com.nationsky.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;
import com.nationsky.webapp.util.CustomDateSerializer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name="schedule_list_user",catalog="emm_backend")
@Indexed
@XmlRootElement
public class ScheduleListUser extends BaseEntity implements Serializable {
    private Long id;
    private Long scheduleId;
    private Long scheduleListId;
    private Long scheduleUserId;
    private Date createTime;

    @Id @GeneratedValue(strategy=IDENTITY) @DocumentId    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    private Schedule schedule = new Schedule();

	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Schedule.class)
	@JoinColumns(value={@JoinColumn(name="schedule_id",referencedColumnName="id",insertable=false,updatable=false)})
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	private ScheduleList scheduleList = new ScheduleList();
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=ScheduleList.class)
	@JoinColumns(value={@JoinColumn(name="schedule_list_id",referencedColumnName="id",insertable=false,updatable=false)})
    public ScheduleList getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(ScheduleList scheduleList) {
		this.scheduleList = scheduleList;
	}
	
	private ScheduleUser scheduleUser = new ScheduleUser();
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=ScheduleUser.class)
	@JoinColumns(value={@JoinColumn(name="schedule_user_id",referencedColumnName="id",insertable=false,updatable=false)})
	public ScheduleUser getScheduleUser() {
		return scheduleUser;
	}

	public void setScheduleUser(ScheduleUser scheduleUser) {
		this.scheduleUser = scheduleUser;
	}

	@Column(name="schedule_id")
    @Field
    public Long getScheduleId() {
        return this.scheduleId;
    }
    
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
    
    @Column(name="schedule_list_id")
    @Field
    public Long getScheduleListId() {
        return this.scheduleListId;
    }
    
    public void setScheduleListId(Long scheduleListId) {
        this.scheduleListId = scheduleListId;
    }
    
    @Column(name="schedule_user_id")
    @Field
    public Long getScheduleUserId() {
        return this.scheduleUserId;
    }
    
    public void setScheduleUserId(Long scheduleUserId) {
        this.scheduleUserId = scheduleUserId;
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

        ScheduleListUser pojo = (ScheduleListUser) o;

        if (scheduleId != null ? !scheduleId.equals(pojo.scheduleId) : pojo.scheduleId != null) return false;
        if (scheduleListId != null ? !scheduleListId.equals(pojo.scheduleListId) : pojo.scheduleListId != null) return false;
        if (scheduleUserId != null ? !scheduleUserId.equals(pojo.scheduleUserId) : pojo.scheduleUserId != null) return false;
        if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (scheduleId != null ? scheduleId.hashCode() : 0);
        result = 31 * result + (scheduleListId != null ? scheduleListId.hashCode() : 0);
        result = 31 * result + (scheduleUserId != null ? scheduleUserId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("scheduleId").append("='").append(getScheduleId()).append("', ");
        sb.append("scheduleListId").append("='").append(getScheduleListId()).append("', ");
        sb.append("scheduleUserId").append("='").append(getScheduleUserId()).append("', ");
        sb.append("createTime").append("='").append(getCreateTime()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
