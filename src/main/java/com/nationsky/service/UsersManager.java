package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.Users;
import com.nationsky.vo.Page;
import com.nationsky.vo.UserVO;

@WebService
public interface UsersManager extends GenericManager<Users, Long> {
    
	public Users findUsers(String username,String password);
	
	public Users findUsersPas(String id,String password);
	
	public UserVO findUser(String loginId,String password);
	
	/**
	 * 管理员列表
	 * @return
	 */
	public Page getUsersList(String pageSize);
	
	public Users getUsersByUserName(String userName);

	public void updateIcon(String iconpath,String userId);
	
}