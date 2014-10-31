package com.nationsky.dao;

import java.util.List;
import java.util.Map;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.Users;
import com.nationsky.vo.MdmUser;
import com.nationsky.vo.Page;
import com.nationsky.vo.UsersVO;

/**
 * An interface that provides a data management interface to the Users table.
 */
public interface UsersDao extends GenericDao<Users, Long> {

	public Users findUsers(String username,String password);
	
	@SuppressWarnings("rawtypes")
	public List findUser(String loginId,String password);
	
	public String findUserOrganizationName(String userId);
	
	public Map<String,Object> findUserByUserId(String userId);
	
	public void insertUser(UsersVO users);
	
	public void updateUser(String tokenId,String userId);
	
	/**
	 * 管理员列表
	 * @return
	 */
	public Page getUsersList(String pageSize);
	
	public Users getUsersByUserName(String userName);
	
	public void updateIcon(String iconpath,String userId);
	
	public Users findUsersPas(String id,String password);
}