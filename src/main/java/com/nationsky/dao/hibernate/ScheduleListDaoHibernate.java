package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.ScheduleListDao;
import com.nationsky.model.ScheduleList;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("scheduleListDao")
public class ScheduleListDaoHibernate extends GenericDaoHibernate<ScheduleList, Long> implements ScheduleListDao {

    public ScheduleListDaoHibernate() {
        super(ScheduleList.class);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<ScheduleList> findShceduleList(String scheduleId) {
		Long scheduleIds = Long.valueOf(scheduleId);
		List<ScheduleList> scheduleList = getSession().createCriteria(ScheduleList.class).add(Restrictions.eq("scheduleId",scheduleIds)).list();
		return scheduleList;
	}

	@Override
	public Page findScheduleList(String pageSize) {
		String hql = " from  ScheduleList order by id desc";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<ScheduleList> scheduleList = query.list();
		Page page = new Page();
		page.setObjList(scheduleList);
		page.setCountPage(size);
		return page;
	}
}
