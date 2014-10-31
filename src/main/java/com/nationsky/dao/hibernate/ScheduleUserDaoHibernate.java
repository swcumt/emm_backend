package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.ScheduleUserDao;
import com.nationsky.model.ScheduleUser;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("scheduleUserDao")
public class ScheduleUserDaoHibernate extends GenericDaoHibernate<ScheduleUser, Long> implements ScheduleUserDao {

    public ScheduleUserDaoHibernate() {
        super(ScheduleUser.class);
    }

	@Override
	public Page findScheduleUser(String pageSize) {
		String hql = " from  ScheduleUser order by id desc";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<ScheduleUser> scheduleUserList = query.list();
		Page page = new Page();
		page.setObjList(scheduleUserList);
		page.setCountPage(size);
		return page;
	}

	@Override
	public List<ScheduleUser> findScheduleUsers(String flag,String userId) {
		try {
			StringBuffer sql = new StringBuffer(" SELECT su.* FROM schedule_user su ") ;
			if("add".equals(flag)){
				sql.append(" WHERE su.id NOT IN ( SELECT sl.schedule_user_id FROM schedule_list_user sl )  ");
			}else if("edit".equals(flag)){
				sql.append(" WHERE su.id NOT IN ( SELECT sl.schedule_user_id FROM schedule_list_user sl )  or su.id = '"+userId+"' ");
			}
			//			String hql = "select su from  ScheduleUser su where su.id not in (select sl.schedule_user_id from ScheduleListUser sl) ";
			Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<ScheduleUser> scheduleUserList = query.list();
			return scheduleUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
