package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.Praise;
@WebService
public interface PraiseManager extends GenericManager<Praise, Long>{

	List<Praise> findPraiseList(String ideaId);

}
