package com.nationsky.dao.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.MdmUsersDao;
import com.nationsky.model.CodeServerUrl;
import com.nationsky.vo.MdmUser;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.IBaseDao;
import com.nationsky.webapp.util.MD5;
import com.nationsky.webapp.util.Utils;
import com.nationsky.webapp.util.impl.BaseDaoImpl;

@Repository("mdmUsersDao")
public class MdmUsersDaoHibernate extends GenericDaoHibernate<MdmUser, Long> implements MdmUsersDao {

	public MdmUsersDaoHibernate() {
		super(MdmUser.class);
	}

	private CodeServerUrl getMdmDbServer() {
		String mdmDbServerId = Utils.getPropertiesValue("MDM_SERVER_CONFIG_ID");
		Criteria criteria = getSession().createCriteria(CodeServerUrl.class);
		criteria.add(Restrictions.eq("id", Long.valueOf(mdmDbServerId)));
		List<CodeServerUrl> li = criteria.list();
		if (li != null && li.size() > 0) {
			return li.get(0);
		}
		return null;
	}

	public Page findMdmUserList(String type, String groupId, String userName, int pageSize, String searchType) throws Exception {
		String tableName = " t_user ";
		String fields = " id, loginId, name, mail, mobile, groupNames, datatype";
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" and (status =1 or status = -1) ");
		if ("ad".equals(type)) {
			whereStr.append(" and datatype = 'AD' ");
		} else if ("mdm".equals(type)) {
			whereStr.append(" and (datatype is null or datatype = '' or datatype = 'MDM') ");
		}

		if (groupId != null && !"".equals(groupId)) {
			whereStr.append(" and groupIds = '" + groupId + "' ");
		}

		if (searchType != null && !"".equals(searchType)) {
			if ("name".equals(searchType)) {
				if (userName != null && !"".equals(userName)) {
					whereStr.append(" and name like '%" + userName + "%' ");
				}
			} else if ("loginId".equals(searchType)) {
				if (userName != null && !"".equals(userName)) {
					whereStr.append(" and loginId like '%" + userName + "%' ");
				}
			} else if ("email".equals(searchType)) {
				if (userName != null && !"".equals(userName)) {
					whereStr.append(" and mail like '%" + userName + "%' ");
				}
			}

		}

		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());

		List<Map<String, Object>> userCount = baseDao.select(tableName, fields, whereStr.toString());
		whereStr.append(" order by creatTime desc LIMIT " + pageSize + "," + Utils.getPropertiesValue("perSize") + " ");
		List<Map<String, Object>> userList = baseDao.select(tableName, fields, whereStr.toString());
		Page page = new Page();
		page.setObjList(userList);
		page.setCountPage(userCount.size());
		return page;
	}

	@Override
	public void deleteMdmUser(String userId) {
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());

		String tableName = " t_user ";
		String settings = " status = 0 ";
		String whereStr = " id = '" + userId + "' ";
		try {
			baseDao.update(tableName, settings, whereStr);
		} catch (Exception e) {
		}
	}

	public Map<String, Object> mdmUserDetail(String userId) {
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		String tableName = "t_user";
		String fields = " * ";
		String whereStr = " and id = '" + userId + "' ";
		Map<String, Object> userMap = null;
		try {
			userMap = baseDao.selectOne(tableName, fields, whereStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userMap;
	}

	@Override
	public String insertOrUpdateUser(MdmUser mdmUser) {
		String result = "";
		if (mdmUser != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String tableName = "t_user";
			String tableNames = "t_group";
			String fields = "*";
			String values = "";
			String settings = "";
			String whereStr = "";
			Map<String, Object> groupMap = null;
			IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());

			if (mdmUser.getId() != null && !"".equals(mdmUser.getId())) {
				try {
					whereStr = " and id = '" + mdmUser.getGroupIds() + "' ";
					groupMap = baseDao.selectOne(tableNames, fields, whereStr);

					String table = "t_user_group";
					settings = "groupId = '" + groupMap.get("id") + "' ";
					whereStr = " userId = '" + mdmUser.getId() + "' ";
					baseDao.update(table, settings, whereStr);

					settings = " loginId = '" + mdmUser.getLoginId() + "',name = '" + mdmUser.getName() + "',mail = '" + mdmUser.getEmail() + "',mobile = '" + mdmUser.getMobile() + "',updateTime = '"
							+ format.format(new Date()) + "' ," + "groupIds = '" + groupMap.get("id") + "',groupNames = '" + groupMap.get("name") + "',datatype = '" + mdmUser.getDatatype() + "'";
					whereStr = " id =  '" + mdmUser.getId() + "' ";
					baseDao.update(tableName, settings, whereStr);
					result = "修改成功";
				} catch (Exception e) {
					result = "修改失败";
				}
			} else {
				try {
					whereStr = " and loginId = '" + mdmUser.getLoginId() + "' ";
					Map<String, Object> usersMap = baseDao.selectOne(tableName, fields, whereStr);
					if (usersMap != null && usersMap.size() > 0) {
						result = "用户已存在";
					} else {
						whereStr = " and id = '" + mdmUser.getGroupIds() + "' ";
						groupMap = baseDao.selectOne(tableNames, fields, whereStr);

						format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						tableName = "t_user";
						fields = "loginid,password,name,mail,mobile,type,canloginself,canregistdevice,caneliminatedevice,"
								+ "allowactivatenum,autosyncontactsflag,conflict,status,creattime,updatetime,allowunregisteredactivate,"
								+ "allowinconsistentactivate,guid,syncbactchno,groupids,groupnames,datatype";
						values = "'" + mdmUser.getLoginId() + "','" + MD5.md5("NQ_" + mdmUser.getPassword() + "^Sky") + "','" + mdmUser.getName() + "','" + mdmUser.getEmail() + "','"
								+ mdmUser.getMobile() + "','0','-1','-1','-1','-1',null,0,'1','" + format.format(new Date()) + "'," + "null,'-1','-1',null,null,'" + groupMap.get("id").toString()
								+ "','" + groupMap.get("name") + "','" + mdmUser.getDatatype() + "'";
						int results = baseDao.insert(tableName, fields, values, true);

						tableName = "t_user_group";
						fields = "userid,groupid";
						values = "'" + results + "','" + groupMap.get("id") + "'";
						baseDao.insert(tableName, fields, values);
						result = "添加成功";
					}

				} catch (Exception e) {
					result = "添加失败";
				}
			}
		}
		return result;
	}

	@Override
	public String updatePassword(String id, String password) {
		String tableName = "t_user";
		String settings = " password = '" + MD5.md5("NQ_" + password + "^Sky") + "' ";
		String whereStr = " id = '" + id + "' ";
		String result = "";
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		try {
			baseDao.update(tableName, settings, whereStr);
			result = "修改成功";
		} catch (Exception e) {
			result = "修改失败";
		}
		return result;
	}

	public String updateUserType(String id, String datatype) {
		String tableName = "t_user";
		String settings = " datatype = '" + datatype + "' ";
		String whereStr = " id = '" + id + "' ";
		String result = "";
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		try {
			baseDao.update(tableName, settings, whereStr);
			result = "修改成功";
		} catch (Exception e) {
			result = "修改失败";
		}
		return result;
	}

	public List<Map<String, Object>> groupList() throws Exception {
		String tableName = "t_group";
		String fields = "id,name";
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());

		List<Map<String, Object>> groupList = baseDao.select(tableName, fields, null);
		return groupList;
	}
	
	public void insertMdmUser(MdmUser mdmUser) {
		if(mdmUser != null){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String tableName = "t_user";
			String tableNames = "t_group";
			String fields = "*";
			String values = "";
			String settings = "";
			String whereStr = "";
			Map<String,Object> groupMap = null;
			IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
			
			try {
				whereStr = " and loginId = '"+mdmUser.getLoginId()+"' ";
				Map<String,Object> usersMap = baseDao.selectOne(tableName, fields, whereStr);
				if(usersMap != null && usersMap.size()>0){
					settings = " password = '"+MD5.md5("NQ_"+mdmUser.getPassword()+"^Sky")+"',status = '1',updateTime = '"+format.format(new Date())+"' ";
					whereStr = "id =  '"+usersMap.get("id")+"'";
					baseDao.update(tableName, settings, whereStr);
				}else{
					whereStr = " and id = '"+mdmUser.getGroupIds()+"' ";
					groupMap = baseDao.selectOne(tableNames, fields, whereStr);
					
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					tableName = "t_user";
					fields = "loginid,password,name,mail,mobile,type,canloginself,canregistdevice,caneliminatedevice," +
							"allowactivatenum,autosyncontactsflag,conflict,status,creattime,updatetime,allowunregisteredactivate," +
							"allowinconsistentactivate,guid,syncbactchno,groupids,groupnames,datatype";
					values = "'"+mdmUser.getLoginId()+"','"+MD5.md5("NQ_"+mdmUser.getPassword()+"^Sky")+"','"+mdmUser.getName()+"',null,null,'0','-1','-1','-1','-1',null,0,'1','"+format.format(new Date())+"'," +
					"null,'-1','-1',null,null,'"+groupMap.get("id").toString()+"','"+groupMap.get("name")+"','MDM'";
					int results = baseDao.insert(tableName, fields, values,true);
					
					tableName = "t_user_group";
					fields = "userid,groupid";
					values = "'"+results+"','"+groupMap.get("id")+"'";
					baseDao.insert(tableName, fields, values);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public void updateMdmUSER(String loginId) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tableName = "t_user";
		String fields = "*";
		String settings = "";
		String whereStr = "";
		IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
		
		try {
			whereStr = " and loginId = '"+loginId+"' ";
			Map<String,Object> usersMap = baseDao.selectOne(tableName, fields, whereStr);
			if(usersMap != null && usersMap.size()>0){
				settings = " status = '0',updateTime = '"+format.format(new Date())+"' ";
				whereStr = "id =  '"+usersMap.get("id")+"'";
				baseDao.update(tableName, settings, whereStr);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	/**
	 * 插入数据
	 * @param map
	 * @throws Exception
	 */
	public void insertData(Map<Integer, String> map) {
		try {
			IBaseDao baseDao = new BaseDaoImpl(getMdmDbServer());
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String tablesql = "alter table t_user add datatype VARCHAR(10)";
			baseDao.alertTable(tablesql);

			for (int i = 1; i <= map.size(); i++) {
				System.out.println(i);
				String[] data = map.get(i).split(",");

				String tableName = "t_group";
				String fields = "*";
				String whereStr = "and name='" + data[2] + "'";

				// 判断用户组是否存在
				Map<String, Object> objMap = baseDao.selectOne(tableName, fields, whereStr);

				if (objMap == null) {

					tableName = "t_group";
					fields = "id,name";
					whereStr = "ORDER BY id desc limit 1";
					objMap = baseDao.selectOne(tableName, fields, whereStr);

					tableName = "t_group";
					fields = "guid,name,parentid,canloginself,canregistdevice,caneliminatedevice,allowactivatenum,"
							+ "type,status,descr,creattime,updatetime,allowunregisteredactivate,allowinconsistentactivate," + "syncbactchno";
					String values = "'" + data[2] + "','" + data[2] + "','-1','-1','-1','-1'," + "'-1','0','1','','" + format.format(new Date()) + "',null,'-1','-1',null";
					// 返回groupid
					int result = baseDao.insert(tableName, fields, values, true);

					tableName = "t_user";
					fields = "*";
					whereStr = " and loginId = '" + data[0] + "' ";

					Map<String, Object> usersMaps = baseDao.selectOne(tableName, fields, whereStr);
					if (usersMaps == null) {
						format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						tableName = "t_user";
						fields = "loginid,password,name,mail,mobile,type,canloginself,canregistdevice,caneliminatedevice,"
								+ "allowactivatenum,autosyncontactsflag,conflict,status,creattime,updatetime,allowunregisteredactivate,"
								+ "allowinconsistentactivate,guid,syncbactchno,groupids,groupnames,datatype";
						values = "'" + data[0] + "','" + MD5.md5("NQ_" + data[0] + "^Sky") + "','" + data[1] + "','" + data[3] + "','" + data[4] + "','0','-1','-1','-1','-1',null,0,'1','"
								+ format.format(new Date()) + "'," + "null,'-1','-1',null,null,'" + result + "','" + data[2] + "','AD'";
						int results = baseDao.insert(tableName, fields, values, true);

						tableName = "t_user_group";
						fields = "userid,groupid";
						values = "'" + results + "','" + result + "'";
						baseDao.insert(tableName, fields, values);
					} else {
						System.out.println(usersMaps.get("name") + ":已存在,更新");
						tableName = "t_user";
						String settings = "loginid='" + data[0] + "',password='" + MD5.md5("NQ_" + data[0] + "^Sky") + "',name='" + data[1] + "',mail='" + data[3] + "',mobile='" + data[4] + "',"
								+ "groupids='" + result + "',groupnames='" + data[2] + "',datatype='AD'";
						whereStr = " id = '" + objMap.get("id") + "' ";
						baseDao.update(tableName, settings, whereStr);
					}

				} else {
					tableName = "t_user";
					fields = "*";
					whereStr = " and loginId = '" + data[0] + "' ";

					Map<String, Object> usersMaps = baseDao.selectOne(tableName, fields, whereStr);
					if (usersMaps == null) {
						format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						tableName = "t_user";
						fields = "loginid,password,name,mail,mobile,type,canloginself,canregistdevice,caneliminatedevice,"
								+ "allowactivatenum,autosyncontactsflag,conflict,status,creattime,updatetime,allowunregisteredactivate,"
								+ "allowinconsistentactivate,guid,syncbactchno,groupids,groupnames,datatype";
						String values = "'" + data[0] + "','" + MD5.md5("NQ_" + data[0] + "^Sky") + "','" + data[1] + "','" + data[3] + "','" + data[4] + "','0','-1','-1','-1','-1',null,0,'1','"
								+ format.format(new Date()) + "'," + "null,'-1','-1',null,null,'" + objMap.get("id") + "','" + objMap.get("name") + "','AD'";
						int results = baseDao.insert(tableName, fields, values, true);

						tableName = "t_user_group";
						fields = "userid,groupid";
						values = "'" + results + "','" + objMap.get("id") + "'";
						baseDao.insert(tableName, fields, values);
					} else {
						System.out.println(usersMaps.get("name") + ":已存在,更新");
						tableName = "t_user";
						String settings = "loginid='" + data[0] + "',password='" + MD5.md5("NQ_" + data[0] + "^Sky") + "',name='" + data[1] + "',mail='" + data[3] + "',mobile='" + data[4] + "',"
								+ "groupids='" + objMap.get("id") + "',groupnames='" + objMap.get("name") + "',datatype='AD'";
						whereStr = " id = '" + usersMaps.get("id") + "' ";
						baseDao.update(tableName, settings, whereStr);
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
