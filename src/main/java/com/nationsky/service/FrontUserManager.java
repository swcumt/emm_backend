package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.FrontUser;

@WebService
public interface FrontUserManager extends GenericManager<FrontUser, String> {

	FrontUser findUsers(String username, String password);

	List<FrontUser> getGroupUsers(String groupId);

	boolean findLoginNameIsExist(String loginName);

	void addall();

}
