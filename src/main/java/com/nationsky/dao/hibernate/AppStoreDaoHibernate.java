package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.AppStoreDao;
import com.nationsky.model.AppStore;
import com.nationsky.vo.AppStoreVO;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Repository("appStoreDao")
public class AppStoreDaoHibernate extends GenericDaoHibernate<AppStore, Long> implements AppStoreDao {

    public AppStoreDaoHibernate() {
        super(AppStore.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<AppStoreVO> appList(Long osId,String modelType) {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select app.app_source appSource,app.appsourceid,app.developerName,app.icon,app.name appName,app.pkgname,app.schemesUrl,app.id appId,app.description as descriptionText,");
    	sql.append(" MAX(edition.versions) version,edition.ipaUrl,edition.appSize,edition.description versionDescription,edition.full_trial_id fullTrialId,edition.id versionId, ");
    	sql.append(" edition.createTime,edition.plistUrl,fullcode.text fullTrialText,");
    	sql.append(" style.id codeStyleId,style.text codeStyleText ");
    	sql.append(" from app_store app ");
    	sql.append(" INNER JOIN appstore_edition edition on (edition.appStoreId = app.id ");
    	if(modelType!=null && !modelType.equals("")){
    		sql.append(" and (edition.code_model="+modelType+" or edition.code_model is null) ");
    	}
    	sql.append(" ) ");
    	sql.append(" INNER JOIN full_trial_code fullcode on fullcode.id =  edition.full_trial_id ");
    	sql.append(" INNER JOIN code_os_style style on style.id=app.code_os_style ");
    	sql.append("where 1=1 ");
    	
		if (osId != null) {
			sql.append(" and ( app.code_os=" + osId + " or app.code_os_style=1)");
		}
    	
    	sql.append(" GROUP BY edition.appStoreId ");
    	
    	Query query  = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	List<AppStoreVO> appList = query.list();
    	return appList;
    }

	@SuppressWarnings("unchecked")
	public List<AppStoreVO> appList(String modelType) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select app.app_source,app.appsourceid,app.developerName,app.icon,app.modelType,app.name NAME,app.pkgname,app.schemesUrl,app.id appstoreid,app.id appId,app.description,app.service_type,");
		sql.append(" MAX(edition.versions) version,edition.ipaUrl,edition.appSize,edition.description versionDescription,edition.full_trial_id,edition.id versionId, ");
		sql.append(" edition.version_type,edition.createTime,edition.plistUrl,fullcode.text fullTrialText ");
		sql.append(" from app_store app ");
		sql.append(" INNER JOIN appstore_edition edition on edition.appStoreId = app.id ");
		sql.append(" INNER JOIN full_trial_code fullcode on  fullcode.id =  edition.full_trial_id ");
		
		if (modelType != null && !modelType.equals("")) {
			sql.append(" where (modelType='" + modelType + "' or modelType = 0  or modelType is null )");
		}
		
		sql.append(" GROUP BY edition.appStoreId ");
		
		Query query  = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<AppStoreVO> appList = query.list();
		return appList;
	}

	@SuppressWarnings("rawtypes")
	public List appInfo(String appId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select app.id,app.icon,app.iconInfo,app.name, edition.appSize,edition.ipaUrl,app.service_type, ");
		sql.append(" MAX(edition.versions) versions,edition.plistUrl,app.schemesUrl,edition.description, ");
		sql.append(" edition.createTime,fullcode.text ");
		sql.append(" from app_store app ");
		sql.append(" INNER JOIN appstore_edition edition ON edition.appStoreId = app.id  ");
		sql.append(" LEFT JOIN full_trial_code fullcode on fullcode.id = edition.full_trial_id ");
		sql.append(" where edition.id = '"+appId+"' ");
		sql.append(" GROUP BY edition.id ");
		
		Query query  = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List appList = query.list();
		return appList;
	}

	@Override
	public Page getAppStore(String pageSize) {
		String hql = " from AppStore order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<AppStore> appStoreList = query.list();
		Page page = new Page();
		page.setObjList(appStoreList);
		page.setCountPage(size);
		return page;
	}
	@Override
	public Page getAppStoreByProject(String pageSize,Long projectId) {
		String hql = " from AppStore where appProject.id = '"+projectId+"') order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		List<AppStore> appStoreList = query.list();
		Page page = new Page();
		page.setObjList(appStoreList);
		page.setCountPage(size);
		return page;
	}
	@Override
	public Page getAppStoreByUser(String pageSize,Long userId) {
			String hql = " from AppStore where appProject.id in (select id from AppProject where users.id='"+userId+"') order by id desc ";
			Query query = getSession().createQuery(hql);
			int size = query.list().size();
			if(null != pageSize){
				query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
				query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
			}
			List<AppStore> appStoreList = query.list();
			Page page = new Page();
			page.setObjList(appStoreList);
			page.setCountPage(size);
			return page;
	}
}
