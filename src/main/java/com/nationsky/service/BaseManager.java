package com.nationsky.service;

import org.appfuse.service.GenericManager;

import com.nationsky.entity.BaseEntity;
import javax.jws.WebService;

@WebService
public interface BaseManager extends GenericManager<BaseEntity, Long> {
    
}