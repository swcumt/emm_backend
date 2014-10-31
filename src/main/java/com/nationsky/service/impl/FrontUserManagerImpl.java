package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.FrontUserDao;
import com.nationsky.model.FrontUser;
import com.nationsky.model.Users;
import com.nationsky.service.FrontUserManager;

@Service("frontUserManager")
@WebService(serviceName = "FrontUserService", endpointInterface = "com.nationsky.service.FrontUserManager")
public class FrontUserManagerImpl extends GenericManagerImpl<FrontUser, String> implements FrontUserManager {

	FrontUserDao frontUserDao;
	@Autowired
	public FrontUserManagerImpl(FrontUserDao frontUserDao) {
		super(frontUserDao);
		this.frontUserDao = frontUserDao;
	}
	@Override
	public FrontUser findUsers(String username, String password) {
		FrontUser user = frontUserDao.findUsers(username,password);
		return user;
	}
	@Override
	public List<FrontUser> getGroupUsers(String groupId) {
		return frontUserDao.getGroupUsers(groupId);
	}
	@Override
	public boolean findLoginNameIsExist(String loginName) {
		List<FrontUser> frontUsers = frontUserDao.findByLoginName(loginName);
		if(frontUsers != null && frontUsers.size() > 0){
			return true;
		}
		return false;
	}
	@Override
	public void addall() {
		FrontUser frontUser = new FrontUser();
		frontUser.setId("11111111111");
		frontUser.setLoginName("1111111111");
		frontUser.setPassword("111111111111");
		frontUser.setName("111111111111111");
		frontUserDao.save(frontUser);
		String a = null;
//		System.out.println(a.equals(""));
	}
	
}
