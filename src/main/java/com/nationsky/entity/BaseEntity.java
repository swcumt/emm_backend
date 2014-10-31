package com.nationsky.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.nationsky.model.AppServiceRelation;
import com.nationsky.model.AppStore;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.model.CodeAppStatus;
import com.nationsky.model.CodeChannel;
import com.nationsky.model.CodeDeveloper;
import com.nationsky.model.CodeOs;
import com.nationsky.model.CodeOsStyle;
import com.nationsky.model.CodeServerUrl;
import com.nationsky.model.DeviceToken;
import com.nationsky.model.FullTrialCode;
import com.nationsky.model.Message;
import com.nationsky.model.P12;
import com.nationsky.model.Service;
import com.nationsky.model.Users;
import com.nationsky.model.Version;

@XmlSeeAlso({ Users.class, Version.class, FullTrialCode.class, AppStore.class, AppstoreEdition.class, P12.class, Service.class, AppServiceRelation.class, DeviceToken.class, Message.class,
		CodeAppStatus.class, CodeChannel.class, CodeDeveloper.class, CodeOs.class, CodeServerUrl.class ,CodeOsStyle.class})
/**
 * Base class for Model objects. Child objects should implement toString(), equals() and hashCode().
 * 
 * @author LeiPeng
 */
public abstract class BaseEntity implements Serializable {

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns a multi-line String with key=value pairs.
	 * 
	 * @return a String representation of this class.
	 */
	public abstract String toString();

	/**
	 * Compares object equality. When using Hibernate, the primary key should
	 * not be a part of this comparison.
	 * 
	 * @param o object to compare to
	 * @return true/false based on equality tests
	 */
	public abstract boolean equals(Object o);

	/**
	 * When you override equals, you should override hashCode. See "Why are
	 * equals() and hashCode() importation" for more information:
	 * http://www.hibernate.org/109.html
	 * 
	 * @return hashCode
	 */
	public abstract int hashCode();
}