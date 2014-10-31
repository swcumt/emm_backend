package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.FullTrialCodeDao;
import com.nationsky.model.FullTrialCode;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("fullTrialCodeDao")
public class FullTrialCodeDaoHibernate extends GenericDaoHibernate<FullTrialCode, Long> implements FullTrialCodeDao {

    public FullTrialCodeDaoHibernate() {
        super(FullTrialCode.class);
    }
    @SuppressWarnings("unchecked")
	@Override
	public boolean exists(FullTrialCode fullTrialCode) {
		Criteria criteria = getSession().createCriteria(FullTrialCode.class);
		criteria.add(Restrictions.eq("text", fullTrialCode.getText()));
		if (null != fullTrialCode.getId()) {
			criteria.add(Restrictions.ne("id", fullTrialCode.getId()));
		}
		List<FullTrialCode> codeList = criteria.list();
		if (codeList != null && codeList.size() > 0) {
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public FullTrialCode findFullTrialCode(String text) {
		FullTrialCode trialCode = null;
		List<FullTrialCode> codeList = getSession().createCriteria(FullTrialCode.class).add(Restrictions.eq("text", text)).list();
		if(codeList!=null && codeList.size()>0){
			trialCode = codeList.get(0);
		}
		return trialCode;
	}

	@Override
	public Page getfindFullTrialCode(String pageSize) {
		String hql = " from  FullTrialCode order by id desc";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<FullTrialCode> fullTrialCodeList = query.list();
		Page page = new Page();
		page.setObjList(fullTrialCodeList);
		page.setCountPage(size);
		return page;
	}
}
