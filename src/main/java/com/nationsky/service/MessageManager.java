package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.Message;

@WebService
public interface MessageManager extends GenericManager<Message, Long> {
	List<Message> getAllByAppId(Long app_version_id);
}