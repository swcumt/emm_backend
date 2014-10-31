package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.P12Dao;
import com.nationsky.model.P12;

@Repository("p12Dao")
public class P12DaoHibernate extends GenericDaoHibernate<P12, Long> implements P12Dao {

	public P12DaoHibernate() {
		super(P12.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<P12> getAllByAppId(Long appVersionId) {
		Criteria criteria = getSession().createCriteria(P12.class);
		criteria.add(Restrictions.eq("appstoreEdition.id", appVersionId));
		return criteria.list();
	}
}
