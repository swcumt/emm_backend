package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.Conversation;

/**
 * An interface that provides a data management interface to the Conversation table.
 */
public interface ConversationDao extends GenericDao<Conversation, Long> {

	Conversation getConsersation(String fromUserId, String toUserId);

	List<Conversation> findConvesation(String userId);

	int findNoReadCount(Long conversationId, String toUserId);

}
