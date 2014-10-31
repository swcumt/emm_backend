package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.ChatRecordDao;
import com.nationsky.model.ChatRecord;
import com.nationsky.service.ChatRecordManager;

@Service("chatRecordManager")
@WebService(serviceName = "ChatRecordManager", endpointInterface = "com.nationsky.service.ChatRecordManager")
public class ChatRecordManagerImpl extends GenericManagerImpl<ChatRecord, Long> implements ChatRecordManager {

	private ChatRecordDao chatRecordDao;

	@Autowired
	public ChatRecordManagerImpl(ChatRecordDao chatRecordDao) {
		super(chatRecordDao);
		this.chatRecordDao = chatRecordDao;
	}

	@Override
	public List<ChatRecord> getRecordListByConversation(String conversationId) {
		return chatRecordDao.getRecordListByConversation(conversationId);
	}

	@Override
	public List<ChatRecord> getRecordPageListByConversation(Long id,
			String startIndex) {
		return chatRecordDao.getRecordPageListByConversation(id,startIndex);
	}

	@Override
	public List<ChatRecord> getRecordPageListByConversation(
			String conversationId, String index) {
		return chatRecordDao.getRecordPageListByConversation(conversationId,index);
	}

	@Override
	public List<ChatRecord> getRecordListByTime(String conversationId,
			String time) {
		return chatRecordDao.getRecordListByTime(conversationId,time);
	}

	@Override
	public List<ChatRecord> getRecordListByTimeSlot(String conversationId,
			String fromTime, String toTime) {
		return chatRecordDao.getRecordListByTimeSlot(conversationId,fromTime,toTime);
	}

	@Override
	public List<ChatRecord> getRecordByTime(Long id, String toUserId,
			String time) {
		List<ChatRecord> recordList = chatRecordDao.getRecordListByTime(id.toString(),time);
		chatRecordDao.updateToReaded(id,toUserId,time);
		return recordList;
	}

	@Override
	public void updateRecordStatus(String conversationId, String toUserId,
			String time) {
		chatRecordDao.updateToReaded(Long.valueOf(conversationId),toUserId,time);		
	}
	
	
}
