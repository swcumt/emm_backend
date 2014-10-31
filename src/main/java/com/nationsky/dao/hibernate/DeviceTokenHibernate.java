package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.DeviceTokenDao;
import com.nationsky.model.DeviceToken;

@Repository("deviceTokenDao")
public class DeviceTokenHibernate extends GenericDaoHibernate<DeviceToken, Long> implements DeviceTokenDao {

	public DeviceTokenHibernate() {
		super(DeviceToken.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceToken> getAllByAppId(Long app_version_id) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(DeviceToken.class);
		criteria.add(Restrictions.eq("appstoreEdition.id", app_version_id));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<String> getTokenByAppId(Long app_version_id) {
		// TODO Auto-generated method stub
		String hql = "select deviceToken from DeviceToken where appstoreEdition.id='" + app_version_id + "'";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
}
