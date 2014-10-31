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
@Table(name="full_trial_code",catalog="emm_backend")
@Indexed
@XmlRootElement
public class FullTrialCode extends BaseEntity implements Serializable {
    private Long id;
    private String text;

    @Id @GeneratedValue(strategy=IDENTITY) @DocumentId    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
	@Column(name="text")
    @Field
    public String getText() {
        return this.text;
    }
    
	public void setText(String text) {
        this.text = text;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullTrialCode pojo = (FullTrialCode) o;

        if (text != null ? !text.equals(pojo.text) : pojo.text != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (text != null ? text.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("text").append("='").append(getText()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
