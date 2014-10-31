package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.IdeaComment;

/**
 * An interface that provides a data management interface to the IdeaComment table.
 */
public interface IdeaCommentDao  extends GenericDao<IdeaComment, Long>{

	List<IdeaComment> findIdeaCommentList(String ideaId);

}
