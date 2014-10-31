package com.nationsky.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.adapter.DateAdapter;
import com.nationsky.entity.BaseEntity;

@Entity
@Table(name="front_user",catalog="emm_backend")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexed
@XmlRootElement(name = "frontUser")
public class FrontUser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;		//用户名
	private String loginName;	//登陆名
	private String password;	//密码
	private String icon;		//头像
	private Long flag;			//标志
	private Integer score;		//积分
	private Date lastLoginTime;	//最后登陆时间
	private Integer seriesLoginCount;	//连续登陆次数
	@Id @DocumentId 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name="name")
    @Field
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="login_name")
    @Field
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@Column(name="password")
    @Field
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="icon")
    @Field
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name="flag")
    @Field
	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}
	@Column(name="score")
    @Field
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastLoginTime", length = 19)
	@Field
	@XmlJavaTypeAdapter(value = DateAdapter.class)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@Column(name="series_login_count")
    @Field
	public Integer getSeriesLoginCount() {
		return seriesLoginCount;
	}

	public void setSeriesLoginCount(Integer seriesLoginCount) {
		this.seriesLoginCount = seriesLoginCount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("flag").append("='").append(getFlag()).append("', ");
        sb.append("name").append("='").append(getName()).append("', ");
        sb.append("password").append("='").append(getPassword()).append("', ");
        sb.append("loginName").append("='").append(getLoginName()).append("', ");
        sb.append("icon").append("='").append(getIcon()).append("', ");
        sb.append("score").append("='").append(getScore()).append("'");
        sb.append("]");
      
        return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrontUser pojo = (FrontUser) o;

        if (flag != null ? !flag.equals(pojo.flag) : pojo.flag != null) return false;
        if (name != null ? !name.equals(pojo.name) : pojo.name != null) return false;
        if (password != null ? !password.equals(pojo.password) : pojo.password != null) return false;
        if (loginName != null ? !loginName.equals(pojo.loginName) : pojo.loginName != null) return false;
        if (icon != null ? !icon.equals(pojo.icon) : pojo.icon != null) return false;
        if (score != null ? !score.equals(pojo.score) : pojo.score != null) return false;

        return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
        result = (flag != null ? flag.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);

        return result;
	}

}
