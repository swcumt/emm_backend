package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.ChatRecord;

@WebService
public interface ChatRecordManager extends GenericManager<ChatRecord, Long>{

	List<ChatRecord> getRecordListByConversation(String conversationId);

	List<ChatRecord> getRecordPageListByConversation(Long id, String startIndex);

	List<ChatRecord> getRecordPageListByConversation(String conversationId,
			String index);

	List<ChatRecord> getRecordListByTime(String conversationId, String time);

	List<ChatRecord> getRecordListByTimeSlot(String conversationId,
			String fromTime, String toTime);

	List<ChatRecord> getRecordByTime(Long id, String toUserId, String time);

	void updateRecordStatus(String conversationId, String fromUserId,
			String time);

}
