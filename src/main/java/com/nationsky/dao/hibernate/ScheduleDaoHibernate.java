package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.ScheduleDao;
import com.nationsky.model.Schedule;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("scheduleDao")
public class ScheduleDaoHibernate extends GenericDaoHibernate<Schedule, Long> implements ScheduleDao {

    public ScheduleDaoHibernate() {
        super(Schedule.class);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public List findScheduleList(String scheduleId) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select l.id,l.schedule_id,l.title,count(t.schedule_user_id) as count ");
		sql.append(" from  schedule_list l,schedule_list_user t ");
		sql.append(" where l.id=t.schedule_list_id and t.schedule_id= '"+scheduleId+"' ");
		sql.append(" group by l.id,l.schedule_id,l.title order by l.id ");
		
		Query query  = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List scheduleList = query.list();
		return scheduleList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findScheduleUser(String schedule_id, String schedule_list_id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id,u.id as schedule_user_id,u.name as schedule_user_name,u.phone_number,u.mobile_phone_number,u.userIcon ");
		sql.append(" from schedule_list_user t,schedule_user u   ");
		sql.append(" where u.id=t.schedule_user_id and t.schedule_id= '"+schedule_id+"' ");
		sql.append(" and t.schedule_list_id= '"+schedule_list_id+"' ");
		sql.append(" order by t.id ");
		
		Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List scheduleUserList = query.list();
		return scheduleUserList;
	}

	@Override
	public Page findShceduleList(String pageSize) {
		String hql = " from  Schedule order by id desc";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		
		List<Schedule> scheduleList = query.list();
		Page page = new Page();
		page.setObjList(scheduleList);
		page.setCountPage(size);
		return page;
	}

}
