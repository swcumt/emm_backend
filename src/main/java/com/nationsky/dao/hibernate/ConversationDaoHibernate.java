package com.nationsky.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.ConversationDao;
import com.nationsky.model.ChatRecord;
import com.nationsky.model.Conversation;

@Repository("conversationDao")
public class ConversationDaoHibernate extends GenericDaoHibernate<Conversation, Long> implements ConversationDao {

	public ConversationDaoHibernate() {
		super(Conversation.class);
	}

	@Override
	public Conversation getConsersation(String fromUserId, String toUserId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Conversation c where 1=1 ");
		sql.append(" and (c.userA.id = '"+fromUserId+"' and c.userB.id = '"+toUserId+"')");
		sql.append(" or (c.userA.id = '"+toUserId+"' and c.userB.id = '"+fromUserId+"')");
		Query query = getSession().createQuery(sql.toString());
		Conversation conversation = (Conversation) query.uniqueResult();
		return conversation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Conversation> findConvesation(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Conversation c where 1=1 ");
		sql.append("and (c.userA.id = '"+userId+"' or c.userB.id = '"+userId+"')");
		Query query = getSession().createQuery(sql.toString());
		List<Conversation> conversations = query.list();
		return conversations;
	}

	@SuppressWarnings("unchecked")
	
	public int findNoReadCount1(Long conversationId, String toUserId) {
		String sql = "from ChatRecord c where c.conversationId = '"+conversationId+"' and c.users.id = '"+toUserId+"' and c.isRead = '0'";
		Query createQuery = getSession().createQuery(sql);
		List<ChatRecord> chatRecordList = createQuery.list();
		int size = chatRecordList.size();
		return size;
	}
	@Override
	public int findNoReadCount(Long conversationId, String toUserId) {
		String sql = "select count(c.id)from ChatRecord c where c.conversationId = '"+conversationId+"' and c.users.id = '"+toUserId+"' and c.isRead = '0'";
		Query createQuery = getSession().createQuery(sql);
		Long count = (Long) createQuery.uniqueResult();
		int size = count.intValue();
		return size;
	}

}
