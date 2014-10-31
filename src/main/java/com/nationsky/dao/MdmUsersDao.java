package com.nationsky.dao;

import java.util.List;
import java.util.Map;

import org.appfuse.dao.GenericDao;

import com.nationsky.vo.MdmUser;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the Users table.
 */
public interface MdmUsersDao extends GenericDao<MdmUser, Long> {

	/**
	 * MDM 用户列表
	 * @param type 账户类型，ad  mdm
	 * @param groupId 用户组
	 * @param userName 用户名
	 * @return
	 * @throws Exception 
	 */
	public Page findMdmUserList(String type,String groupId,String userName,int pageSize,String searchType) throws Exception;
	
	/**
	 * 删除用户
	 * @param userId
	 * @throws Exception
	 */
	public void deleteMdmUser(String userId);
	
	/**
	 * 用户详细信息
	 * @param userId
	 * @return
	 */
	public Map<String,Object> mdmUserDetail(String userId);
	
	/**
	 * 添加修改用户信息
	 * @param mdmUser
	 */
	public String insertOrUpdateUser(MdmUser mdmUser);
	
	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @return
	 */
	public String updatePassword(String id, String password);
	
	/**
	 * 修改用户类别
	 * @param id
	 * @param datatype
	 * @return
	 */
	public String updateUserType(String id, String datatype);
	
	/**
	 * 查询用户组
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> groupList() throws Exception;
	public void insertData(Map<Integer,String> map);
	
	public void insertMdmUser(MdmUser mdmUser);
	
	public void updateMdmUSER(String loginId);
	
}