package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.FrontUserGroup;

@WebService
public interface FrontUserGroupManager extends GenericManager<FrontUserGroup, Long> {

}
