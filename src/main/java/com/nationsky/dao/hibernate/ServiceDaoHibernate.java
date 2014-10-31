package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.ServiceDao;
import com.nationsky.model.AppStore;
import com.nationsky.model.Service;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("serviceDao")
public class ServiceDaoHibernate extends GenericDaoHibernate<Service, Long> implements ServiceDao {

	public ServiceDaoHibernate() {
		super(Service.class);
	}

	@Override
	public Page getServiceList(String pageSize) {
		String hql = " from Service order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<Service> serviceList = query.list();
		Page page = new Page();
		page.setObjList(serviceList);
		page.setCountPage(size);
		return page;
	}
}
