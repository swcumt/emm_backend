package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.AppTypeDao;
import com.nationsky.model.AppType;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("appTypeDao")
public class AppTypeDaoHibernate extends GenericDaoHibernate<AppType, String> implements AppTypeDao {

    public AppTypeDaoHibernate() {
        super(AppType.class);
    }

	@Override
	public Page findAppType(String pageSize) {
		String hql = " from AppType order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(pageSize != null){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		
		List<AppType> appTypeList = query.list();
		Page page = new Page();
		page.setObjList(appTypeList);
		page.setCountPage(size);
		return page;
	}

}
