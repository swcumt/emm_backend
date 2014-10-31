package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.AppProjectDao;
import com.nationsky.model.AppProject;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("appProjectDao")
public class AppProjectDaoHibernate extends GenericDaoHibernate<AppProject, Long> implements AppProjectDao {

	public AppProjectDaoHibernate() {
		super(AppProject.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(AppProject appProject,Long id) {
		Criteria criteria = getSession().createCriteria(AppProject.class);
		criteria.add(Restrictions.eq("name", appProject.getName()));
		criteria.add(Restrictions.eq("users.id", id));
		if (null != appProject.getId()) {
			criteria.add(Restrictions.ne("id", appProject.getId()));
		}
		List<AppProject> appProjectList = criteria.list();
		if (appProjectList != null && appProjectList.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page getList(String pageSize,Long id) {
		Criteria criteria = getSession().createCriteria(AppProject.class);
		criteria.add(Restrictions.eq("users.id", id));
		int size = criteria.list().size();
		if (null != pageSize) {
			criteria.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			criteria.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize")) * (Integer.valueOf(pageSize) - 1));
		}
		criteria.addOrder(Order.desc("id"));
		List<AppProject> appProjectList = criteria.list();
		Page page = new Page();
		page.setObjList(appProjectList);
		page.setCountPage(size);
		return page;
	}
}
