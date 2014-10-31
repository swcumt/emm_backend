package com.nationsky.dao.hibernate;

import java.util.List;

import com.nationsky.model.ScheduleListUser;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;
import com.nationsky.dao.ScheduleListUserDao;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("scheduleListUserDao")
public class ScheduleListUserDaoHibernate extends GenericDaoHibernate<ScheduleListUser, Long> implements ScheduleListUserDao {

    public ScheduleListUserDaoHibernate() {
        super(ScheduleListUser.class);
    }

	@Override
	public Page findScheduleListUser(String pageSize) {
		String hql = " from  ScheduleListUser order by id desc";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<ScheduleListUser> scheduleListUser = query.list();
		Page page = new Page();
		page.setObjList(scheduleListUser);
		page.setCountPage(size);
		return page;
	}
}
