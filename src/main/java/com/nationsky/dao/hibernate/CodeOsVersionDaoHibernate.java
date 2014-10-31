package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CodeOsVersionDao;
import com.nationsky.model.CodeOsVersion;
import com.nationsky.model.CodeOsVersion;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("codeOsVersionDao")
public class CodeOsVersionDaoHibernate extends GenericDaoHibernate<CodeOsVersion, Long> implements CodeOsVersionDao {

	public CodeOsVersionDaoHibernate() {
		super(CodeOsVersion.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(CodeOsVersion codeOsVersion) {
		Criteria criteria = getSession().createCriteria(CodeOsVersion.class);
		criteria.add(Restrictions.eq("text", codeOsVersion.getText()));
		if (null != codeOsVersion.getId()) {
			criteria.add(Restrictions.ne("id", codeOsVersion.getId()));
		}
		List<CodeOsVersion> codeList = criteria.list();
		if (codeList != null && codeList.size() > 0) {
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public CodeOsVersion exists(String text) {
		CodeOsVersion codeOs = null;
		List<CodeOsVersion> codeList = getSession().createCriteria(CodeOsVersion.class).add(Restrictions.eq("text", text)).list();
		if (codeList != null && codeList.size() > 0) {
			codeOs = codeList.get(0);
		}
		return codeOs;
	}

	@Override
	public List<CodeOsVersion> getAllByParentId(Long pid) {
		Criteria criteria = getSession().createCriteria(CodeOsVersion.class);
		criteria.add(Restrictions.eq("codeOs.id", pid));
		List<CodeOsVersion> codeList = criteria.list();
		return codeList;
	}

	@Override
	public Page codeOsList(String pageSize) {
		String hql = " from CodeOsVersion order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<CodeOsVersion> codeOsList = query.list();
		Page page = new Page();
		page.setObjList(codeOsList);
		page.setCountPage(size);
		return page;
	}
}
