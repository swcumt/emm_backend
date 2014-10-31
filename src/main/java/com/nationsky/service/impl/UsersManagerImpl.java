package com.nationsky.service.impl;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.UsersDao;
import com.nationsky.model.Users;
import com.nationsky.service.UsersManager;
import com.nationsky.vo.Page;
import com.nationsky.vo.UserVO;
import com.nationsky.vo.UsersVO;
import com.nationsky.webapp.util.Utils;

@Service("usersManager")
@WebService(serviceName = "UsersService", endpointInterface = "com.nationsky.service.UsersManager")
public class UsersManagerImpl extends GenericManagerImpl<Users, Long> implements UsersManager {
    UsersDao usersDao;

    @Autowired
    public UsersManagerImpl(UsersDao usersDao) {
        super(usersDao);
        this.usersDao = usersDao;
    }

	@Override
	public Users findUsers(String username,String password){
		Users user = usersDao.findUsers(username,password);
		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public UserVO findUser(String loginId,String password) {
		List list = usersDao.findUser(loginId,password);
		UserVO user = null;
		String tokenId = Utils.getUUID();
		if(list.size() > 0 && list != null){
			Map map = (Map)list.get(0);
			user = new UserVO();
			user.setId(map.get("id").toString());
			user.setNiceName(map.get("name") == null
					|| "null".equals(map.get("name")) ? "未填写" : map.get("name").toString());
			user.setPhone(map.get("mobile") == null
					|| "null".equals(map.get("mobile")) ? "未填写" : map.get("mobile").toString());
			user.setEmail(map.get("mail") == null
					|| "null".equals(map.get("mail")) ? "未填写" : map.get("mail").toString());
			user.setIcon(map.get("icon") == null
					|| "null".equals(map.get("icon")) ? "未填写" : map.get("icon").toString());
			user.setIntroduction(map.get("introduction") == null
					|| "null".equals(map.get("introduction")) ? "未填写"
					: map.get("introduction").toString());
			user.setLandline(map.get("landline") == null
					|| "null".equals(map.get("landline")) ? "未填写" : map.get("landline").toString());
			user.setPosition(map.get("position")== null
					|| "null".equals(map.get("position")) ? "未填写" : map.get("position").toString());
			user.setSex(map.get("sex") == null
					|| "null".equals(map.get("sex")) ? "未填写" : map.get("sex").toString());
			user.setLoginTime(map.get("loginTime") == null || "null".equals(map.get("loginTime")) ? "未知" : map.get("loginTime").toString());
		
		}
			
		if(user != null){
			Map<String,Object> userMap = usersDao.findUserByUserId(user.getId());
			if(userMap!=null && userMap.size()>0){
				usersDao.updateUser(tokenId, user.getId());
			}else{
				UsersVO usersVo = new UsersVO();
				usersVo.setTokenId(tokenId);
				usersVo.setId(user.getId());
				usersDao.insertUser(usersVo);
			}
			
			String organizationName = usersDao.findUserOrganizationName(user.getId());
			user.setOrganizationName(organizationName);
		}
		
		return user;
	}

	@Override
	public Page getUsersList(String pageSize) {
		return usersDao.getUsersList(pageSize);
	}

	@Override
	public Users getUsersByUserName(String userName) {
		return usersDao.getUsersByUserName(userName);
	}
	
	public void updateIcon(String iconpath, String userId) {
		usersDao.updateIcon(iconpath, userId);
	}

	@Override
	public Users findUsersPas(String id, String password) {
		return usersDao.findUsersPas(id, password);
	}

}