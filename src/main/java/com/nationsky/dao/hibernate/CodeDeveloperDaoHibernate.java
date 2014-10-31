package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CodeDeveloperDao;
import com.nationsky.model.CodeDeveloper;
import com.nationsky.model.CodeDeveloper;

@Repository("codeDeveloper")
public class CodeDeveloperDaoHibernate extends GenericDaoHibernate<CodeDeveloper, Long> implements CodeDeveloperDao {

	public CodeDeveloperDaoHibernate() {
		super(CodeDeveloper.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(CodeDeveloper codeDeveloper) {
		Criteria criteria = getSession().createCriteria(CodeDeveloper.class);
		criteria.add(Restrictions.eq("developerName", codeDeveloper.getDeveloperName()));
		if (null != codeDeveloper.getId()) {
			criteria.add(Restrictions.ne("id", codeDeveloper.getId()));
		}
		List<CodeDeveloper> codeList = criteria.list();
		if (codeList != null && codeList.size() > 0) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CodeDeveloper exists(String text) {
		CodeDeveloper codeDeveloper = null;
		List<CodeDeveloper> codeList = getSession().createCriteria(CodeDeveloper.class).add(Restrictions.eq("text", text)).list();
		if (codeList != null && codeList.size() > 0) {
			codeDeveloper = codeList.get(0);
		}
		return codeDeveloper;
	}
}
