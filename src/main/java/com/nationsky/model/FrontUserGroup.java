package com.nationsky.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;

@Entity
@Table(name="front_group",catalog="emm_backend")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexed
@XmlRootElement(name = "frontUserGroup")
public class FrontUserGroup extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String groupName;
	private Integer count;	//组员人数
	
	@Id @GeneratedValue(strategy=IDENTITY) @DocumentId  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="group_name")
    @Field
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Column(name="count")
    @Field
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("count").append("='").append(getCount()).append("', ");
        sb.append("groupName").append("='").append(getGroupName()).append("' ");
        sb.append("]");
      
        return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrontUserGroup pojo = (FrontUserGroup) o;

        if (id != null ? !id.equals(pojo.id) : pojo.id != null) return false;
        if (groupName != null ? !groupName.equals(pojo.groupName) : pojo.groupName != null) return false;
        if (count != null ? !count.equals(pojo.count) : pojo.count != null) return false;

        return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
        result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);

        return result;
	}

}
