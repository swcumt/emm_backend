package com.nationsky.dao;

import java.util.List;
import java.util.Map;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.AppComment;
import com.nationsky.vo.Page;

/**
 * An interface that provides a data management interface to the AComment table.
 */
public interface AppCommentDao extends GenericDao<AppComment, String> {

	public Page findCommentList(String appId,String pageSize);
	
	public List<AppComment> findComment(String appId);
	
	public AppComment findComment(String appId, String userId);
	
	@SuppressWarnings("rawtypes")
	public List findCommentScoreList(String appId);
	
	public Map<String,Object> findCommentUser(String userId);
	
	/**
	 * 我的评论
	 * @param appId
	 * @param userId
	 * @return
	 */
	public List<AppComment> myComment(String appId,String userId);

	public Map<String, Object> findAppCommentUser(String userId);
}