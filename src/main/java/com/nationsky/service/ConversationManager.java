package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.Conversation;

@WebService
public interface ConversationManager extends GenericManager<Conversation, Long> {

	Conversation getConsersation(String fromUserId, String toUserId);

	List<Conversation> findConvesation(String userId);

}
