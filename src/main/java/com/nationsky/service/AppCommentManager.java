package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.AppComment;
import com.nationsky.vo.CommentVO;
import com.nationsky.vo.Page;

@WebService
public interface AppCommentManager extends GenericManager<AppComment, String> {
	public Page findCommentList(String appId,String pageSize) ;
	
	public AppComment findComment(String appId,String userId);
	
	public CommentVO findCommentScoreList(String appId);
	
	/**
	 * 我的评论
	 * @param appId
	 * @param userId
	 * @return
	 */
	public List<AppComment> myComment(String appId,String userId);

	public CommentVO findAppCommentScoreList(String appId);

	
}