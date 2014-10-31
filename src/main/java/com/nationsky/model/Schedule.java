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
@Table(name="schedule",catalog="emm_backend")
@Indexed
@XmlRootElement
public class Schedule extends BaseEntity implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Date createTime;
    private String icon;
    private String icons;

    @Id @GeneratedValue(strategy=IDENTITY) @DocumentId    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    private List<ScheduleList> scheduleList = new ArrayList<ScheduleList>();

    @OneToMany(fetch = FetchType.EAGER,targetEntity = ScheduleList.class,cascade =      //单项一对多配置
        {
        CascadeType.REMOVE
        })
        @JoinColumns(value={@JoinColumn(name="schedule_id",referencedColumnName="id",insertable=false,updatable=false)})
    @Fetch(FetchMode.SELECT)
    @XmlTransient
	@JsonIgnore 
    public List<ScheduleList> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<ScheduleList> scheduleList) {
		this.scheduleList = scheduleList;
	}
	
	private List<ScheduleListUser> scheduleListUser = new ArrayList<ScheduleListUser>();

	@OneToMany(fetch = FetchType.EAGER,targetEntity = ScheduleListUser.class,cascade =      //单项一对多配置
        {
        CascadeType.REMOVE
        })
        @JoinColumns(value={@JoinColumn(name="schedule_id",referencedColumnName="id",insertable=false,updatable=false)})
    @Fetch(FetchMode.SELECT)
    @XmlTransient
	@JsonIgnore
	public List<ScheduleListUser> getScheduleListUser() {
		return scheduleListUser;
	}

	public void setScheduleListUser(List<ScheduleListUser> scheduleListUser) {
		this.scheduleListUser = scheduleListUser;
	}

	@Column(name="title")
    @Field
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="content", length=500)
    @Field
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
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
    
    @Column(name="icon", length=200)
    @Field
    public String getIcon() {
        return this.icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    @Column(name="icons", length=200)
    @Field
    public String getIcons() {
        return this.icons;
    }
    
    public void setIcons(String icons) {
        this.icons = icons;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule pojo = (Schedule) o;

        if (title != null ? !title.equals(pojo.title) : pojo.title != null) return false;
        if (content != null ? !content.equals(pojo.content) : pojo.content != null) return false;
        if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;
        if (icon != null ? !icon.equals(pojo.icon) : pojo.icon != null) return false;
        if (icons != null ? !icons.equals(pojo.icons) : pojo.icons != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (icons != null ? icons.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("title").append("='").append(getTitle()).append("', ");
        sb.append("content").append("='").append(getContent()).append("', ");
        sb.append("createTime").append("='").append(getCreateTime()).append("', ");
        sb.append("icon").append("='").append(getIcon()).append("', ");
        sb.append("icons").append("='").append(getIcons()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
