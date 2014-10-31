package com.nationsky.dao.hibernate;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.FrontUserGroupDao;
import com.nationsky.model.FrontUserGroup;

@Repository("frontUserGroupDao")
public class FrontUserGroupDaoHibernate extends GenericDaoHibernate< FrontUserGroup, Long> implements  FrontUserGroupDao {

	public FrontUserGroupDaoHibernate() {
		super(FrontUserGroup.class);
	}

}
