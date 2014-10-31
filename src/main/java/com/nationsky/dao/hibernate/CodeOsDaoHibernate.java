package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CodeOsDao;
import com.nationsky.model.CodeOs;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("codeOsDao")
public class CodeOsDaoHibernate extends GenericDaoHibernate<CodeOs, Long> implements CodeOsDao {

	public CodeOsDaoHibernate() {
		super(CodeOs.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(CodeOs codeOs) {
		Criteria criteria = getSession().createCriteria(CodeOs.class);
		criteria.add(Restrictions.eq("text", codeOs.getText()));
		if (null != codeOs.getId()) {
			criteria.add(Restrictions.ne("id", codeOs.getId()));
		}
		List<CodeOs> codeList = criteria.list();
		if (codeList != null && codeList.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CodeOs exists(String text) {
		CodeOs codeOs = null;
		List<CodeOs> codeList = getSession().createCriteria(CodeOs.class).add(Restrictions.eq("text", text)).list();
		if (codeList != null && codeList.size() > 0) {
			codeOs = codeList.get(0);
		}
		return codeOs;
	}

	@Override
	public List<CodeOs> getAllByParentId(Long pid) {
		Criteria criteria = getSession().createCriteria(CodeOs.class);
		criteria.add(Restrictions.eq("parentId", pid));
		List<CodeOs> codeList = criteria.list();
		return codeList;
	}

	@Override
	public Page codeOsList(String pageSize) {
		String hql = " from CodeOs order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if (null != pageSize) {
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize")) * (Integer.valueOf(pageSize) - 1));
		}
		List<CodeOs> codeOsList = query.list();
		Page page = new Page();
		page.setObjList(codeOsList);
		page.setCountPage(size);
		return page;
	}
}
