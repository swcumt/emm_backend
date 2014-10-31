package com.nationsky.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.UsersDao;
import com.nationsky.model.CodeServerUrl;
import com.nationsky.model.Users;
import com.nationsky.utils.Crypt;
import com.nationsky.vo.Page;
import com.nationsky.vo.UsersVO;
import com.nationsky.webapp.util.IBaseDao;
import com.nationsky.webapp.util.MD5;
import com.nationsky.webapp.util.Utils;
import com.nationsky.webapp.util.impl.BaseDaoImpl;

@Repository("usersDao")
public class UsersDaoHibernate extends GenericDaoHibernate<Users, Long> implements UsersDao {

    public UsersDaoHibernate() {
        super(Users.class);
    }
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
	@SuppressWarnings("unchecked")
	@Override
	public Users findUsers(String username,String password) {
		Users user = null; 
		List<Users> userList = getSession().createCriteria(Users.class).add(Restrictions.eq("username", username)).add(Restrictions.eq("password", MD5.md5("NQ_"+password+"^Sky"))).list();
		if(userList!=null && userList.size()>0){
			user = userList.get(0);
		}
		 return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findUser(String loginId,String password) {
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		String tableName = " t_user as tuser left join a_user as auser on auser.id = tuser.id ";
		String fields = " tuser.id,tuser.name,tuser.mail,tuser.mobile,auser.sex,auser.age,auser.address,auser.icon,auser.introduction,auser.landline,auser.position,auser.loginTime ";
		String whereStr = " and  tuser.loginId = '" + loginId+ "' ";
		if(password != null && !"".equals(password)){
			whereStr += "  and password = '"+MD5.md5("NQ_"+password+"^Sky")+"' ";
		}
		try {
			return baseDao.select(tableName, fields, whereStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String findUserOrganizationName(String userId) {
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		String name = null;
		String tableName = " t_group as groups inner join t_user_group as usergroup on usergroup.groupId = groups.id  ";
		String fields = " groups.name as groupName ";
		String whereStr = " and  usergroup.userId = '" + userId + "' ";
		Map map = null;
		try {
			map = baseDao.selectOne(tableName, fields, whereStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(map != null){
			name = map.get("groupName")==null?"":map.get("groupName").toString();
		}
		return name;
	}

	@Override
	public Map<String, Object> findUserByUserId(String userId) {
		String tableName = " a_user ";
		String fields = " * ";
		String whereStr = " and id = '"+userId+"' ";
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		Map<String,Object> userMap = null;
		try {
			userMap = baseDao.selectOne(tableName, fields, whereStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userMap;
	}

	public void insertUser(UsersVO usersVo) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tableName = "a_user";
		String fields = " id,sex,age,address,tokenId,position,icon,introduction,landline,loginTime ";
		String values =  " '"+ usersVo.getId()+ "','"+ usersVo.getSex()+ "','"+ usersVo.getAge()+ "','"+ usersVo.getAddress()
			+ "','"+ usersVo.getTokenId()+ "','"+ usersVo.getPosition()+ "','"+ usersVo.getIcon()+ "','"
			+ usersVo.getIntroduction()+ "','"+ usersVo.getLandline() + "','"+sim.format(new Date())+"' ";
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		try {
			baseDao.insert(tableName, fields, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUser(String tokenId, String userId) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tableName = "a_user";
		String settings = " tokenId = '"+tokenId+"',loginTime = '"+sim.format(new Date())+"' ";
		String whereStr = " id = '"+userId+"' ";
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		try {
			baseDao.update(tableName, settings, whereStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Page getUsersList(String pageSize) {
		String hql = " from Users order by id desc ";
		Query query = getSession().createQuery(hql);
		int size = query.list().size();
		if(null != pageSize){
			query.setMaxResults(Integer.valueOf(Utils.getPropertiesValue("perSize")));
			query.setFirstResult(Integer.valueOf(Utils.getPropertiesValue("perSize"))*(Integer.valueOf(pageSize)-1)); 
		}
		
		List<Users> userList = query.list();
		Page page = new Page();
		page.setObjList(userList);
		page.setCountPage(size);
		return page;
	}
	@Override
	public void updateIcon(String iconpath, String userId) {
		// TODO Auto-generated method stub
		 String tableName = "a_user";
	        String settings = " icon = '"+iconpath+"' ";
	        String whereStr = " id = '"+userId+"'  ";
	        IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
	        try {
				baseDao.update(tableName, settings, whereStr);
			} catch (Exception e) {
				
			}
	}


	@Override
	public Users getUsersByUserName(String userName) {
		Users user = null; 
		List<Users> userList = getSession().createCriteria(Users.class).add(Restrictions.eq("username", userName)).list();
		if(userList!=null && userList.size()>0){
			user = userList.get(0);
		}
		 return user;
	}
	@Override
	public Users findUsersPas(String id, String password) {
		Users user = null; 
		List<Users> userList = getSession().createCriteria(Users.class).add(Restrictions.eq("id", new Long(id))).add(Restrictions.eq("password", MD5.md5("NQ_"+password+"^Sky"))).list();
		if(userList!=null && userList.size()>0){
			user = userList.get(0);
		}
		 return user;
	}
}
