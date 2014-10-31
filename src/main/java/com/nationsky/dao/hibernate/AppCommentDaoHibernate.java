package com.nationsky.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.AppCommentDao;
import com.nationsky.model.AppComment;
import com.nationsky.model.CodeServerUrl;
import com.nationsky.model.FrontUser;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.IBaseDao;
import com.nationsky.webapp.util.Utils;
import com.nationsky.webapp.util.impl.BaseDaoImpl;

@Repository("aCommentDao")
public class AppCommentDaoHibernate extends GenericDaoHibernate<AppComment, String> implements AppCommentDao {
	private CodeServerUrl getMdmDbServer(){
    	String mdmDbServerId = Utils.getPropertiesValue("MDM_SERVER_CONFIG_ID");
    	Criteria criteria = getSession().createCriteria(CodeServerUrl.class);
		criteria.add(Restrictions.eq("id", Long.valueOf(mdmDbServerId)));
		List<CodeServerUrl> li = criteria.list();
		if(li!=null && li.size()>0){
			return li.get(0);
		}
		return null;
    }
	public AppCommentDaoHibernate() {
        super(AppComment.class);
    }

	@SuppressWarnings("unchecked")
	@Override
	public Page findCommentList(String appId,String pageSize) {
		String hql = " from AppComment where versionId = '"+appId+"'  order by createTime desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
		query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		List<AppComment> commentList = query.list();
		Page page = new Page();
		page.setObjList(commentList);
		page.setCountPage(size);
		return page;
	}

	@SuppressWarnings("unchecked")
	public AppComment findComment(String appId, String userId) {
		List<AppComment> commentList = getSession().createCriteria(AppComment.class).add(Restrictions.eq("versionId", String.valueOf(appId))).add(Restrictions.eq("userId", String.valueOf(userId))).add(Restrictions.eq("flag", Long.valueOf(1))).list();
		AppComment comment = null;
		if(commentList.size()>0){
			comment = commentList.get(0);
		}
		return comment;
	}

	@SuppressWarnings("rawtypes")
	public List findCommentScoreList(String appId) {
		List entryList = new ArrayList();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select apps.*,(apps.score*apps.scoreCount) as counts from ( ");
			sql.append("select feed.versionId,feed.score,count(feed.score) as scoreCount,feed.flag ");
			sql.append(" from a_comment as feed ");
			sql.append(" where feed.versionId = '"+appId+"' and feed.flag = 1 ");
			sql.append(" group by feed.score) as apps ");
			
			Query query  = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			entryList = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return entryList;
	}

	public Map<String, Object> findCommentUser(String userId) {
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		String tableName = " a_user as user inner join t_user as tuser on tuser.id = user.id ";
		String fields =  " user.icon as uicon,user.position as position,tuser.name as niceName ";
		String whereStr = " and user.id = '"+userId+"' ";
		Map<String,Object> map = null;
		try {
			map =  baseDao.selectOne(tableName, fields, whereStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<AppComment> findComment(String appId) {
		List<AppComment> commentList = getSession().createCriteria(AppComment.class).add(Restrictions.eq("versionId", appId)).addOrder(Order.desc("createTime")).list();
		return commentList;
	}
	
	public List<AppComment> myComment(String appId, String userId) {
		List<AppComment> commentList = getSession().createCriteria(AppComment.class).add(Restrictions.eq("versionId", appId)).add(Restrictions.eq("userId", userId)).addOrder(Order.desc("createTime")).list();
		return commentList;
	}
	@Override
	public Map<String, Object> findAppCommentUser(String userId) {
		String hql = " from FrontUser u where u.id = '"+userId+"'";
		Query query = getSession().createQuery(hql);
		List<FrontUser> userList = query.list();
		Map<String,Object> map = null;
		if(userList != null && userList.size() > 0){
			FrontUser user = userList.get(0);
			map = new HashMap<String, Object>();
			map.put("name", user.getName());
			map.put("icon", user.getIcon());
		}
		return map;
	}

}
