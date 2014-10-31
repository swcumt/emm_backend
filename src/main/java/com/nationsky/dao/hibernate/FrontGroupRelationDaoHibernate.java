package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.FrontGroupRelationDao;
import com.nationsky.model.FrontGroupRelation;

@Repository("frontGroupRelationDao")
public class FrontGroupRelationDaoHibernate extends GenericDaoHibernate<FrontGroupRelation, Long> implements FrontGroupRelationDao {

	public FrontGroupRelationDaoHibernate() {
		super(FrontGroupRelation.class);
	}

	@Override
	public boolean delete(String groupId, String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from FrontGroupRelation f where f.group.id = '"+groupId+"' and f.user.id = '"+userId+"'");
		Query query = getSession().createQuery(sql.toString());
		int count = query.executeUpdate();
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrontGroupRelation find(Long groupId, String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from FrontGroupRelation f where f.group.id = '"+groupId+"' and f.user.id = '"+userId+"'");
		Query query = getSession().createQuery(sql.toString());
		List<FrontGroupRelation> list = query.list();
		FrontGroupRelation relation = null;
		if(list != null && list.size() > 0){
			relation = list.get(0);
		}
		return relation;
	}

}
