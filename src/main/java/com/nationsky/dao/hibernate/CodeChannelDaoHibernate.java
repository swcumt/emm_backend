package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.CodeChannelDao;
import com.nationsky.model.AppStore;
import com.nationsky.model.CodeChannel;
import com.nationsky.model.CodeChannel;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("codeChannelDao")
public class CodeChannelDaoHibernate extends GenericDaoHibernate<CodeChannel, Long> implements CodeChannelDao {

	public CodeChannelDaoHibernate() {
		super(CodeChannel.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(CodeChannel codeChannel) {
		Criteria criteria = getSession().createCriteria(CodeChannel.class);
		criteria.add(Restrictions.eq("text", codeChannel.getText()));
		if (null != codeChannel.getId()) {
			criteria.add(Restrictions.ne("id", codeChannel.getId()));
		}
		List<CodeChannel> codeList = criteria.list();
		if (codeList != null && codeList.size() > 0) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CodeChannel exists(String text) {
		CodeChannel codeChannel = null;
		List<CodeChannel> codeList = getSession().createCriteria(CodeChannel.class).add(Restrictions.eq("text", text)).list();
		if (codeList != null && codeList.size() > 0) {
			codeChannel = codeList.get(0);
		}
		return codeChannel;
	}

	@Override
	public Page codeChannelList(String pageSize) {
		String hql = " from CodeChannel order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<CodeChannel> codeChannelList = query.list();
		Page page = new Page();
		page.setObjList(codeChannelList);
		page.setCountPage(size);
		return page;
	}
}
