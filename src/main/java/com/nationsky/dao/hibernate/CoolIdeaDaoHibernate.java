package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CoolIdeaDao;
import com.nationsky.model.CoolIdea;

@Repository("coolIdeaDao")
public class CoolIdeaDaoHibernate extends GenericDaoHibernate<CoolIdea, Long> implements CoolIdeaDao{

	public CoolIdeaDaoHibernate() {
		super(CoolIdea.class);
	}
	@Override
	public List<CoolIdea> getAllNewest() {
		StringBuffer sql = new StringBuffer();
		sql.append("from CoolIdea c order by c.commentTime desc");
		Query query = getSession().createQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<CoolIdea> ideas = query.list();
		return ideas;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CoolIdea> getAllHeatest() {
		StringBuffer sql = new StringBuffer();
//		sql.append("select c.id,c.content,c.createTime,c.commentNumber,c.praiseNumber,c.commentTime,c.flag,c.users.id,c.users.name,c.users.username,c.users.icon from CoolIdea c order by c.commentNumber desc");
		sql.append(" from CoolIdea c order by c.commentNumber desc");
		Query query = getSession().createQuery(sql.toString());
		List<CoolIdea> ideas = query.list();
		return ideas;
	}
	@Override
	public List<CoolIdea> getAllAdopted() {
		StringBuffer sql = new StringBuffer();
		sql.append("from CoolIdea c where c.flag = '1' order by c.commentTime desc");
		Query query = getSession().createQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<CoolIdea> ideas = query.list();
		return ideas;
	}
	@Override
	public List<CoolIdea> getAllByFlag(String flag) {
		StringBuffer sql = new StringBuffer();
		sql.append("from CoolIdea c where c.flag = ? order by c.commentTime desc");
		Query query = getSession().createQuery(sql.toString());
		query.setString(0, flag);
		@SuppressWarnings("unchecked")
		List<CoolIdea> ideas = query.list();
		return ideas;
	}

}
