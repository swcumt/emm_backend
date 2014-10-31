package com.nationsky.model;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name="a_comment",catalog="emm_backend")
@Indexed
@XmlRootElement
public class AppComment extends BaseEntity implements Serializable {
    private String id;
    private String versionId;
    private String userId;
    private String createTime;
    private String score;
    private String conntext;
    private Long flag;

    @Id  @DocumentId    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="versionId", length=32)
    @Field
    public String getVersionId() {
        return this.versionId;
    }
    
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }
    
    @Column(name="userId", length=32)
    @Field
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name="createTime", length=50)
    @Field
    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="score")
    @Field
    public String getScore() {
        return this.score;
    }
    
    public void setScore(String score) {
        this.score = score;
    }
    
    @Column(name="conntext", length=5000)
    @Field
    public String getConntext() {
        return this.conntext;
    }
    
    public void setConntext(String conntext) {
        this.conntext = conntext;
    }
    
    @Column(name="flag")
    @Field
    public Long getFlag() {
        return this.flag;
    }
    
    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppComment pojo = (AppComment) o;

        if (versionId != null ? !versionId.equals(pojo.versionId) : pojo.versionId != null) return false;
        if (userId != null ? !userId.equals(pojo.userId) : pojo.userId != null) return false;
        if (createTime != null ? !createTime.equals(pojo.createTime) : pojo.createTime != null) return false;
        if (score != null ? !score.equals(pojo.score) : pojo.score != null) return false;
        if (conntext != null ? !conntext.equals(pojo.conntext) : pojo.conntext != null) return false;
        if (flag != null ? !flag.equals(pojo.flag) : pojo.flag != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (versionId != null ? versionId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (conntext != null ? conntext.hashCode() : 0);
        result = 31 * result + (flag != null ? flag.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("versionId").append("='").append(getVersionId()).append("', ");
        sb.append("userId").append("='").append(getUserId()).append("', ");
        sb.append("createTime").append("='").append(getCreateTime()).append("', ");
        sb.append("score").append("='").append(getScore()).append("', ");
        sb.append("conntext").append("='").append(getConntext()).append("', ");
        sb.append("flag").append("='").append(getFlag()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
