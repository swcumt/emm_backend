package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.FrontUserDao;
import com.nationsky.model.FrontUser;

@Repository("frontUserDao")
public class FrontUserDaoHibernate extends GenericDaoHibernate< FrontUser, String> implements  FrontUserDao {

	public FrontUserDaoHibernate() {
		super(FrontUser.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrontUser findUsers(String username, String password) {
		FrontUser user = null; 
		List<FrontUser> userList = getSession().createCriteria(FrontUser.class).add(Restrictions.eq("loginName", username)).add(Restrictions.eq("password", password)).list();
		if(userList!=null && userList.size()>0){
			user = userList.get(0);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FrontUser> getGroupUsers(String groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from FrontUser f where 1=1 ");
		sql.append("and f.id in (select r.user.id from FrontGroupRelation r where r.group.id = '"+groupId+"')");
		Query query = getSession().createQuery(sql.toString());
		List<FrontUser> userList = query.list();
		return userList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FrontUser> findByLoginName(String loginName) {
		List<FrontUser> userList = getSession().createCriteria(FrontUser.class).add(Restrictions.eq("loginName", loginName)).list();
		return userList;
	}

}
