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

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name="version",catalog="emm_backend")
@Indexed
@XmlRootElement
public class Version extends BaseEntity implements Serializable {
    private Long id;
    private String versionId;
    private String content;
    private String url;
    private Date createTime;

    @Id @GeneratedValue(strategy=IDENTITY) @DocumentId    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="versionId", length=100)
    @Field
    public String getVersionId() {
        return this.versionId;
    }
    
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }
    
    @Column(name="content", length=65535)
    @Field
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="url", length=500)
    @Field
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createTime", length=19)
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

        Version pojo = (Version) o;

        if (versionId != null ? !versionId.equals(pojo.versionId) : pojo.versionId != null) return false;
        if (content != null ? !content.equals(pojo.content) : pojo.content != null) return false;
        if (url != null ? !url.equals(pojo.url) : pojo.url != null) return false;
        if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (versionId != null ? versionId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("versionId").append("='").append(getVersionId()).append("', ");
        sb.append("content").append("='").append(getContent()).append("', ");
        sb.append("url").append("='").append(getUrl()).append("', ");
        sb.append("createTime").append("='").append(getCreateTime()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
