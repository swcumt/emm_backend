package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.ChatRecordDao;
import com.nationsky.model.ChatRecord;
import com.nationsky.webapp.util.DateUtil;

@Repository("chatRecordDao")
public class ChatRecordDaoHibernate extends GenericDaoHibernate<ChatRecord, Long> implements ChatRecordDao {

	public ChatRecordDaoHibernate() {
		super(ChatRecord.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChatRecord> getRecordListByConversation(String conversationId) {
		Long id = Long.valueOf(conversationId);
		List<ChatRecord> chatRecordList = getSession().createCriteria(ChatRecord.class).add(Restrictions.eq("conversationId",id)).addOrder(Order.asc("createTime")).list();
		return chatRecordList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChatRecord> getRecordPageListByConversation(Long id,
			String startIndex) {
		Integer index = Integer.valueOf(startIndex);
		List<ChatRecord> chatRecordList = getSession().createCriteria(ChatRecord.class).add(Restrictions.eq("conversationId",id)).addOrder(Order.asc("createTime")).setFirstResult(index).setMaxResults(10).list();
		return chatRecordList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChatRecord> getRecordPageListByConversation(
			String conversationId, String index) {
		Long id = Long.valueOf(conversationId);
		Integer indexs = Integer.valueOf(index);
		List<ChatRecord> chatRecordList = getSession().createCriteria(ChatRecord.class).add(Restrictions.eq("conversationId",id)).addOrder(Order.asc("createTime")).setFirstResult(indexs).setMaxResults(10).list();
		return chatRecordList;
	}

	@Override
	public List<ChatRecord> getRecordListByTime(String conversationId,
			String time) {
		Long times = Long.valueOf(time);
		Long from = Long.valueOf(times);
		String fromStr = DateUtil.millisToDateStr(from, "yyyy-MM-dd HH:mm:ss");
		String sql = " from ChatRecord c where c.conversationId = 'CONID' and c.createTime > 'FROM' order by c.createTime";
		sql = sql.replaceAll("FROM", fromStr).replaceAll("CONID", conversationId);
		Query createSQLQuery = getSession().createQuery(sql);
		@SuppressWarnings("unchecked")
		
		List<ChatRecord> chatRecordList = createSQLQuery.list();
//		List<ChatRecord> chatRecordList = getSession().createCriteria(ChatRecord.class).add(Restrictions.eq("conversationId",id)).add(Restrictions.lt("createTime", date)).addOrder(Order.asc("createTime")).list();
		return chatRecordList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChatRecord> getRecordListByTimeSlot(String conversationId,
			String fromTime, String toTime) {
		Long from = Long.valueOf(fromTime);
		Long to = Long.valueOf(toTime);
		String fromStr = DateUtil.millisToDateStr(from, "yyyy-MM-dd HH:mm:ss");
		String toStr = DateUtil.millisToDateStr(to, "yyyy-MM-dd HH:mm:ss");
		String sql = " from ChatRecord c where c.conversationId = 'CONID' and  c.createTime > 'FROM' and c.createTime < 'TO' order by c.createTime";
		sql = sql.replaceAll("FROM", fromStr).replaceAll("TO", toStr).replaceAll("CONID", conversationId);
		Query createSQLQuery = getSession().createQuery(sql);
		List<ChatRecord> chatRecordList = createSQLQuery.list();
//		List<ChatRecord> chatRecordList = getSession().createCriteria(ChatRecord.class).add(Restrictions.eq("conversationId",id)).add(Restrictions.lt("createTime", dateFrom)).add(Restrictions.gt("createTime", dateTo)).addOrder(Order.asc("createTime")).list();
		return chatRecordList;
	}

	@Override
	public void updateToReaded(Long id, String fromUserId, String time) {
		Long from = Long.valueOf(time);
		String fromStr = DateUtil.millisToDateStr(from, "yyyy-MM-dd HH:mm:ss");
		String sql = "update chat_record c set c.isRead='1' where c.conversation_id = '"+id+"' and c.from_user_id = '"+fromUserId+"' and c.createTime > '"+fromStr+"'";
		Query createSQLQuery = getSession().createSQLQuery(sql);
		createSQLQuery.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findNoReadCount(Long conversationId, String toUserId) {
		String sql = "from ChatRecord c where c.conversationId = '"+conversationId+"' and c.users.id = '"+toUserId+"' and c.isRead = '0'";
		Query createQuery = getSession().createQuery(sql);
		List<ChatRecord> chatRecordList = createQuery.list();
		int size = chatRecordList.size();
		return size;
	}

}
