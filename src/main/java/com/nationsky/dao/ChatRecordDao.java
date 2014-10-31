package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.ChatRecord;

/**
 * An interface that provides a data management interface to the ChatRecord table.
 */
public interface ChatRecordDao extends GenericDao<ChatRecord, Long> {

	List<ChatRecord> getRecordListByConversation(String conversationId);

	List<ChatRecord> getRecordPageListByConversation(Long id, String startIndex);

	List<ChatRecord> getRecordPageListByConversation(String conversationId,
			String index);

	List<ChatRecord> getRecordListByTime(String conversationId, String time);

	List<ChatRecord> getRecordListByTimeSlot(String conversationId,
			String fromTime, String toTime);

	void updateToReaded(Long id, String toUserId, String time);

	int findNoReadCount(Long conversationId, String toUserId);

}
