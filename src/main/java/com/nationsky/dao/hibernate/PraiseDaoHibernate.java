package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.PraiseDao;
import com.nationsky.model.Praise;

@Repository("praiseDao")
public class PraiseDaoHibernate extends GenericDaoHibernate<Praise, Long> implements PraiseDao{

	public PraiseDaoHibernate() {
		super(Praise.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Praise> findPraiseList(String ideaId) {
		Long ideaIdIds = Long.valueOf(ideaId);
		List<Praise> praiseList = getSession().createCriteria(Praise.class).add(Restrictions.eq("coolIdeaId",ideaIdIds)).addOrder(Order.desc("createTime")).list();
		return praiseList;
	}

}
