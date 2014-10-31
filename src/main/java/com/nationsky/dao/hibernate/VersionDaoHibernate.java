package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.VersionDao;
import com.nationsky.model.AppType;
import com.nationsky.model.Version;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("versionDao")
public class VersionDaoHibernate extends GenericDaoHibernate<Version, Long> implements VersionDao {

    public VersionDaoHibernate() {
        super(Version.class);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Version> getMaxVersion() {
		String sql = "select * from version order by createTime desc limit 0,1";
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);;
		List<Version> versionList = query.list();
		return versionList;
	}

	@Override
	public Page getVersionList(String pageSize) {
		String hql = " from  Version order by id desc";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		
		List<Version> versionList = query.list();
		Page page = new Page();
		page.setObjList(versionList);
		page.setCountPage(size);
		return page;
	}
}
