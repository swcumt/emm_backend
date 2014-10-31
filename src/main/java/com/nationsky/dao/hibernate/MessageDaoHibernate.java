package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.MessageDao;
import com.nationsky.model.Message;

@Repository("messageDao")
public class MessageDaoHibernate extends GenericDaoHibernate<Message, Long> implements MessageDao {

	public MessageDaoHibernate() {
		super(Message.class);
	}

	@Override
	public List<Message> getAllByAppId(Long app_version_id) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Message.class);
		criteria.add(Restrictions.eq("appstoreEdition.id", app_version_id));
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
}
