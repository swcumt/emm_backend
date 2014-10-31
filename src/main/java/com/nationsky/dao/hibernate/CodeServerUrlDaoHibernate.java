package com.nationsky.dao.hibernate;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CodeServerUrlDao;
import com.nationsky.model.CodeServerUrl;

@Repository("codeServerUrlDao")
public class CodeServerUrlDaoHibernate extends GenericDaoHibernate<CodeServerUrl, Long> implements CodeServerUrlDao {

	public CodeServerUrlDaoHibernate() {
		super(CodeServerUrl.class);
	}
//
//
//	@Override
//	public List<CodeServerUrl> getServerUrlList() {
//		Criteria criteria = getSession().createCriteria(CodeServerUrl.class);
//		return criteria.list();
//	}
//
//	@Override
//	public CodeServerUrl getServerUrlById(Long id) {
//		Criteria criteria = getSession().createCriteria(CodeServerUrl.class);
//		criteria.add(Restrictions.eq("id", pid));
//		return null;
//	}
}
