package com.nationsky.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.AppServiceRelationDao;
import com.nationsky.model.AppServiceRelation;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.model.P12;
import com.nationsky.model.Service;

@Repository("appServiceRelationDao")
public class AppServiceRelationDaoHibernate extends GenericDaoHibernate<AppServiceRelation, Long> implements AppServiceRelationDao {

	public AppServiceRelationDaoHibernate() {
		super(AppServiceRelation.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public AppServiceRelation get(Long appVersionId, Long serviceId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AppServiceRelation.class);
		criteria.add(Restrictions.eq("appstoreEdition.id", appVersionId));
		criteria.add(Restrictions.eq("service.id", serviceId));
		List<AppServiceRelation> appServiceRelationList = criteria.list();
		if (appServiceRelationList != null && appServiceRelationList.size() > 0) {
			return appServiceRelationList.get(0);
		}
		return null;
	}

	@Override
	public void remove(Long appVersionId, Long serviceId) {
		// TODO Auto-generated method stub
		Criteria p12Criteria = getSession().createCriteria(P12.class);
		p12Criteria.add(Restrictions.eq("appstoreEdition.id", appVersionId));
		if (p12Criteria.list() != null && p12Criteria.list().size() > 0) {
			getSession().delete(p12Criteria.list().get(0));
		}

		Criteria criteria = getSession().createCriteria(AppServiceRelation.class);
		criteria.add(Restrictions.eq("appstoreEdition.id", appVersionId));
		criteria.add(Restrictions.eq("service.id", serviceId));
		getSession().delete(criteria.list().get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Service> getServiceListByApp(Long appVersionId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AppServiceRelation.class);
		criteria.add(Restrictions.eq("appstoreEdition.id", appVersionId));
		List<AppServiceRelation> appServiceRelationList = criteria.list();
		List<Service> serviceList = new ArrayList<Service>();
		for (AppServiceRelation appServiceRelation : appServiceRelationList) {
			serviceList.add(appServiceRelation.getService());
		}
		return serviceList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppstoreEdition> getAppListByService(Long serviceId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AppServiceRelation.class);
		criteria.add(Restrictions.eq("service.id", serviceId));
		List<AppServiceRelation> appServiceRelationList = criteria.list();
		List<AppstoreEdition> serviceList = new ArrayList<AppstoreEdition>();
		for (AppServiceRelation appServiceRelation : appServiceRelationList) {
			serviceList.add(appServiceRelation.getAppstoreEdition());
		}
		return serviceList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppServiceRelation> getListByAppVersion(Long appVersionId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AppServiceRelation.class);
		criteria.add(Restrictions.eq("appstoreEdition.id", appVersionId));
		List<AppServiceRelation> appServiceRelationList = criteria.list();
		return appServiceRelationList;
	}

}
