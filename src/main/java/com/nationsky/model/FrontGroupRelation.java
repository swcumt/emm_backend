package com.nationsky.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;

@Entity
@Table(name="front_group_user_relation",catalog="emm_backend")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexed
@XmlRootElement(name = "frontUserGroup")
public class FrontGroupRelation extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private FrontUserGroup group;
	private FrontUser user;
	
	@Id @GeneratedValue(strategy=IDENTITY) @DocumentId  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "group_id")
	public FrontUserGroup getGroup() {
		return group;
	}

	public void setGroup(FrontUserGroup group) {
		this.group = group;
	}
	@ManyToOne
	@JoinColumn(name = "user_id")
	public FrontUser getUser() {
		return user;
	}

	public void setUser(FrontUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("user").append("='").append(getUser()).append("', ");
        sb.append("group").append("='").append(getGroup()).append("' ");
        sb.append("]");
      
        return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrontGroupRelation pojo = (FrontGroupRelation) o;

        if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
        if (user != null ? !user.equals(pojo.user) : pojo.user != null) return false;
        if (group != null ? !group.equals(pojo.group) : pojo.group != null) return false;

        return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
        result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);

        return result;
	}

}
