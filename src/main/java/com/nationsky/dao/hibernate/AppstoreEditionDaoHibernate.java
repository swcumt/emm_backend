package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.AppstoreEditionDao;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("appstoreEditionDao")
public class AppstoreEditionDaoHibernate extends GenericDaoHibernate<AppstoreEdition, Long> implements AppstoreEditionDao {

    public AppstoreEditionDaoHibernate() {
        super(AppstoreEdition.class);
    }

	@Override
	public Page getAppstoreEdition(String appid,String pageSize) {
		String hql = " from  AppstoreEdition where appStoreId = '"+appid+"' order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
		query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		List<AppstoreEdition> appstoreEditionList = query.list();
		Page page = new Page();
		page.setObjList(appstoreEditionList);
		page.setCountPage(size);
		return page;
	}

	@Override
	public AppstoreEdition getAppStoreEdition(String version,String appId) {
		AppstoreEdition appstoreEdition = null;
		List<AppstoreEdition> versionList = getSession().createCriteria(AppstoreEdition.class).add(Restrictions.eq("versions", version)).add(Restrictions.eq("appStoreId", appId)).list();
		if(versionList!=null && versionList.size()>0){
			appstoreEdition = versionList.get(0);
		}
		return appstoreEdition;
	}

	public AppstoreEdition checkVersion(String version,String appId) {
		AppstoreEdition appstoreEdition = null;
		String hql = " from  AppstoreEdition where appStoreId = '"+appId+"' and versions > '"+version+"' order by id desc ";
		Query query = getSession().createQuery(hql);
		List<AppstoreEdition> versionList = query.list();
		if(versionList!=null && versionList.size()>0){
			appstoreEdition = versionList.get(0);
		}
		return appstoreEdition;
	}

	@Override
	public List<AppstoreEdition> historyApp(String appId) {
		String hql = " from  AppstoreEdition where appStoreId = '"+appId+"' order by id desc ";
		Query query = getSession().createQuery(hql);
		List<AppstoreEdition> appstoreEditionList = query.list();
		return appstoreEditionList;
	}

	@Override
	public AppstoreEdition getMaxVersion(String appId) {
		// TODO Auto-generated method stub
		AppstoreEdition appstoreEdition = null;
		Criteria criteria = getSession().createCriteria(AppstoreEdition.class);
		criteria.addOrder(Order.desc("id"));
		criteria.add(Restrictions.eq("appStoreId", Long.valueOf(appId)));
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		List<AppstoreEdition> versionList = criteria.list();
		if (versionList != null && versionList.size() > 0) {
			appstoreEdition = versionList.get(0);
		}
		return appstoreEdition;
	}
}
