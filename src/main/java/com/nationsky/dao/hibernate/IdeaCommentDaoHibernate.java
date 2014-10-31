package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.IdeaCommentDao;
import com.nationsky.model.IdeaComment;

@Repository("ideaCommentDao")
public class IdeaCommentDaoHibernate extends GenericDaoHibernate<IdeaComment, Long> implements IdeaCommentDao{

	public IdeaCommentDaoHibernate() {
		super(IdeaComment.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IdeaComment> findIdeaCommentList(String ideaId) {
		Long ideaIdIds = Long.valueOf(ideaId);
		List<IdeaComment> ideaCommentList = getSession().createCriteria(IdeaComment.class).add(Restrictions.eq("coolIdeaId",ideaIdIds)).addOrder(Order.desc("createTime")).list();
		return ideaCommentList;
	}

}
