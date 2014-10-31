package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CodeModelDao;
import com.nationsky.model.CodeModel;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("codeModelDao")
public class CodeModelDaoHibernate extends GenericDaoHibernate<CodeModel, Long> implements CodeModelDao {

	public CodeModelDaoHibernate() {
		super(CodeModel.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(CodeModel codeModel) {
		Criteria criteria = getSession().createCriteria(CodeModel.class);
		criteria.add(Restrictions.eq("text", codeModel.getText()));
		if (null != codeModel.getId()) {
			criteria.add(Restrictions.ne("id", codeModel.getId()));
		}
		List<CodeModel> codeList = criteria.list();
		if (codeList != null && codeList.size() > 0) {
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public CodeModel exists(String text) {
		CodeModel codeOs = null;
		List<CodeModel> codeList = getSession().createCriteria(CodeModel.class).add(Restrictions.eq("text", text)).list();
		if (codeList != null && codeList.size() > 0) {
			codeOs = codeList.get(0);
		}
		return codeOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page codeOsList(String pageSize) {
		String hql = " from CodeModel order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<CodeModel> codeOsList = query.list();
		Page page = new Page();
		page.setObjList(codeOsList);
		page.setCountPage(size);
		return page;
	}
}
