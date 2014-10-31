package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CodeAppStatusDao;
import com.nationsky.model.AppStore;
import com.nationsky.model.CodeAppStatus;
import com.nationsky.model.CodeAppStatus;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("codeAppStatusDao")
public class CodeAppStatusDaoHibernate extends GenericDaoHibernate<CodeAppStatus, Long> implements CodeAppStatusDao {

	public CodeAppStatusDaoHibernate() {
		super(CodeAppStatus.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(CodeAppStatus codeAppStatus) {
		Criteria criteria = getSession().createCriteria(CodeAppStatus.class);
		criteria.add(Restrictions.eq("text", codeAppStatus.getText()));
		if (null != codeAppStatus.getId()) {
			criteria.add(Restrictions.ne("id", codeAppStatus.getId()));
		}
		List<CodeAppStatus> codeList = criteria.list();
		if (codeList != null && codeList.size() > 0) {
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public CodeAppStatus exists(String text) {
		CodeAppStatus codeAppStatus = null;
		List<CodeAppStatus> codeList = getSession().createCriteria(CodeAppStatus.class).add(Restrictions.eq("text", text)).list();
		if (codeList != null && codeList.size() > 0) {
			codeAppStatus = codeList.get(0);
		}
		return codeAppStatus;
	}

	@Override
	public Page codeAppStatusList(String pageSize) {
		String hql = " from CodeAppStatus order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<CodeAppStatus> codeAppStatusList = query.list();
		Page page = new Page();
		page.setObjList(codeAppStatusList);
		page.setCountPage(size);
		return page;
	}
	
	
}
