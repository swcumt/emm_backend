package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CodeOsStyleDao;
import com.nationsky.model.CodeOsStyle;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("codeOsStyleDao")
public class CodeOsStyleDaoHibernate extends GenericDaoHibernate<CodeOsStyle, Long> implements CodeOsStyleDao {

	public CodeOsStyleDaoHibernate() {
		super(CodeOsStyle.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(CodeOsStyle codeOsStyle) {
		Criteria criteria = getSession().createCriteria(CodeOsStyle.class);
		criteria.add(Restrictions.eq("text", codeOsStyle.getText()));
		if (null != codeOsStyle.getId()) {
			criteria.add(Restrictions.ne("id", codeOsStyle.getId()));
		}
		List<CodeOsStyle> codeList = criteria.list();
		if (codeList != null && codeList.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CodeOsStyle exists(String text) {
		CodeOsStyle codeOsStyle = null;
		List<CodeOsStyle> codeList = getSession().createCriteria(CodeOsStyle.class).add(Restrictions.eq("text", text)).list();
		if (codeList != null && codeList.size() > 0) {
			codeOsStyle = codeList.get(0);
		}
		return codeOsStyle;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CodeOsStyle> getAllByParentId(Long pid) {
		Criteria criteria = getSession().createCriteria(CodeOsStyle.class);
		criteria.add(Restrictions.eq("parentId", pid));
		List<CodeOsStyle> codeList = criteria.list();
		return codeList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page codeOsStyleList(String pageSize) {
		String hql = " from CodeOsStyle order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if (null != pageSize) {
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize")) * (Integer.valueOf(pageSize) - 1));
		}
		List<CodeOsStyle> codeOsStyleList = query.list();
		Page page = new Page();
		page.setObjList(codeOsStyleList);
		page.setCountPage(size);
		return page;
	}
}
