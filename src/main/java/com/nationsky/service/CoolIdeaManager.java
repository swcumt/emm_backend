package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.CoolIdea;
@WebService
public interface CoolIdeaManager extends GenericManager<CoolIdea, Long>{

	List<CoolIdea> getAllNewest();

	List<CoolIdea> getAllHeatest();

	List<CoolIdea> getAllAdopted();

	List<CoolIdea> getAllByFlag(String flag);

}
