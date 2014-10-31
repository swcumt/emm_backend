package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.ChatRecordDao;
import com.nationsky.dao.ConversationDao;
import com.nationsky.model.Conversation;
import com.nationsky.service.ConversationManager;

@Service("conversationManager")
@WebService(serviceName = "ConversationManager", endpointInterface = "com.nationsky.service.ConversationManager")
public class ConversationManageImpl extends GenericManagerImpl<Conversation, Long> implements ConversationManager {

	private ConversationDao conversationDao;
	@Autowired
	public ConversationManageImpl(ConversationDao conversationDao) {
		super(conversationDao);
		this.conversationDao = conversationDao;
	}

	@Override
	public Conversation getConsersation(String fromUserId, String toUserId) {
		return conversationDao.getConsersation(fromUserId,toUserId);
	}

	

	@Override
	public List<Conversation> findConvesation(String userId) {
		List<Conversation> convesationList = conversationDao.findConvesation(userId);
		for (Conversation conversation : convesationList) {
			String toUserId = null;
			if(conversation.getUserA().getId().equals(userId)){
				toUserId = conversation.getUserB().getId();
			}else{
				toUserId = conversation.getUserA().getId();
			}
			int count = conversationDao.findNoReadCount(conversation.getId(),toUserId);
			conversation.noReadCount = count;
		}
		return convesationList;
	}
	
	
}
