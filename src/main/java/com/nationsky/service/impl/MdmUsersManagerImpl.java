package com.nationsky.service.impl;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.MdmUsersDao;
import com.nationsky.service.MdmUsersManager;
import com.nationsky.vo.MdmUser;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Service("mdmUsersManager")
@WebService(serviceName = "MdmUsersService", endpointInterface = "com.nationsky.service.MdmUsersManager")
public class MdmUsersManagerImpl extends GenericManagerImpl<MdmUser, Long> implements MdmUsersManager {
    MdmUsersDao mdmUsersDao;

    @Autowired
    public MdmUsersManagerImpl(MdmUsersDao mdmUsersDao) {
        super(mdmUsersDao);
        this.mdmUsersDao = mdmUsersDao;
    }

	public Page findMdmUserList(String type,
			String groupId, String userName,String pageSize,String searchType ) throws Exception {
		int fromSize = 0;//分页使用
		int perSize = Integer.valueOf(Utils.getPropertiesValue("perSize"));//每页显示的条数
		System.out.println(pageSize);
		if(!"".equals(pageSize) && null != pageSize && !"null".equals(pageSize)){
			if(pageSize.equals("0")){
				pageSize ="1";
			}
			int s=Integer.parseInt(pageSize);
			fromSize=perSize*(s-1);
		}
		
		return mdmUsersDao.findMdmUserList(type, groupId, userName,fromSize,searchType);
	}

	@Override
	public void deleteMdmUser(String userId)  {
		mdmUsersDao.deleteMdmUser(userId);
	}

	@Override
	public MdmUser mdmUserDetail(String userId) {
		Map<String,Object> userMap = mdmUsersDao.mdmUserDetail(userId);
		MdmUser mdmUser  = new MdmUser();
		if(userMap != null && userMap.size()>0){
			mdmUser.setId(userMap.get("id").toString());
			mdmUser.setLoginId(userMap.get("loginId").toString());
			mdmUser.setName(userMap.get("name").toString());
			mdmUser.setDatatype(userMap.get("datatype") == null ? "" : userMap.get("datatype").toString());
			mdmUser.setEmail(userMap.get("mail") == null ? "" : userMap.get("mail").toString());
			mdmUser.setMobile(userMap.get("mobile") == null ? "" : userMap.get("mobile").toString());
			mdmUser.setPassword(userMap.get("password").toString());
			mdmUser.setGroupIds(userMap.get("groupIds").toString());
			mdmUser.setGroupNames(userMap.get("groupNames").toString());
		}
		
		return mdmUser;
	}

	@Override
	public String  insertOrUpdateUser(MdmUser mdmUser) {
		String result = mdmUsersDao.insertOrUpdateUser(mdmUser);
		return result;
	}

	public String updatePassword(String id, String password) {
		String result  = mdmUsersDao.updatePassword(id, password);
		return result;
	}

	@Override
	public String updateUserType(String id, String datatype) {
		return mdmUsersDao.updateUserType(id, datatype);
	}

	public List<Map<String, Object>> groupList() throws Exception {
		return mdmUsersDao.groupList();
	}

	@Override
	public void insertMdmUser(MdmUser mdmUser) {
		mdmUsersDao.insertMdmUser(mdmUser);
	}

	@Override
	public void updateMdmUSER(String loginId) {
		mdmUsersDao.updateMdmUSER(loginId);
	}
	
	public void insertData(Map<Integer, String> map) {
		// TODO Auto-generated method stub
		mdmUsersDao.insertData(map);
	}

}