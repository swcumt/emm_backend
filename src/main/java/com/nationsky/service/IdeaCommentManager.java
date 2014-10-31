package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.IdeaComment;

@WebService
public interface IdeaCommentManager  extends GenericManager<IdeaComment, Long>{

	List<IdeaComment> findIdeaCommentList(String ideaId);

}
