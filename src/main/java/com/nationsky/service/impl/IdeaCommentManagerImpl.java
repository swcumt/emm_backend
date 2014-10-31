package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.IdeaCommentDao;
import com.nationsky.model.IdeaComment;
import com.nationsky.service.IdeaCommentManager;

@Service("ideaCommentManager")
@WebService(serviceName = "IdeaCommentService", endpointInterface = "com.nationsky.service.IdeaCommentManager")
public class IdeaCommentManagerImpl  extends GenericManagerImpl<IdeaComment, Long> implements IdeaCommentManager{

	private IdeaCommentDao ideaCommentDao;
	@Autowired
	public IdeaCommentManagerImpl(IdeaCommentDao ideaCommentDao) {
		super(ideaCommentDao);
		this.ideaCommentDao = ideaCommentDao;
	}
	@Override
	public List<IdeaComment> findIdeaCommentList(String ideaId) {
		return ideaCommentDao.findIdeaCommentList(ideaId);
	}

	
}
