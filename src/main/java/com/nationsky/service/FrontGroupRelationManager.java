package com.nationsky.service;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.FrontGroupRelation;
@WebService
public interface FrontGroupRelationManager extends GenericManager<FrontGroupRelation, Long> {

	boolean delete(String groupId, String userId);

	FrontGroupRelation find(Long groupId, String userId);

}
