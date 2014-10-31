package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.MessageDao;
import com.nationsky.model.Message;
import com.nationsky.service.MessageManager;

@Service("messageManager")
@WebService(serviceName = "MessageService", endpointInterface = "com.nationsky.service.MessageManager")
public class MessageManagerImpl extends GenericManagerImpl<Message, Long> implements MessageManager {
	MessageDao messageDao;

	@Autowired
	public MessageManagerImpl(MessageDao messageDao) {
		super(messageDao);
		this.messageDao = messageDao;
	}

	@Override
	public List<Message> getAllByAppId(Long app_version_id) {
		return messageDao.getAllByAppId(app_version_id);
	}
}