package com.nationsky.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.AppCommentDao;
import com.nationsky.model.AppComment;
import com.nationsky.service.AppCommentManager;
import com.nationsky.vo.AppScoreVO;
import com.nationsky.vo.AppStoreCommentVO;
import com.nationsky.vo.CommentVO;
import com.nationsky.vo.Page;

@Service("aCommentManager")
@WebService(serviceName = "ACommentService", endpointInterface = "com.nationsky.service.ACommentManager")
public class AppCommentManagerImpl extends GenericManagerImpl<AppComment, String> implements AppCommentManager {
    AppCommentDao aCommentDao;

    @Autowired
    public AppCommentManagerImpl(AppCommentDao aCommentDao) {
        super(aCommentDao);
        this.aCommentDao = aCommentDao;
    }

	@Override
	public Page findCommentList(String appId,String pageSize) {
		return aCommentDao.findCommentList(appId,pageSize);
	}

	public AppComment findComment(String appId, String userId) {
		return aCommentDao.findComment(appId, userId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CommentVO findCommentScoreList(String appId) {
		List<AppComment> commentList =aCommentDao.findComment(appId);
		CommentVO commentVo = new CommentVO();
		
		if(commentList.size()>0){
    		//查询人员信息
    		List<AppStoreCommentVO> appcommentList = new ArrayList<AppStoreCommentVO>();//评论列表
    		for(int i=0;i<commentList.size();i++){
    			AppStoreCommentVO storeComment = new AppStoreCommentVO();
    			AppComment comment = commentList.get(i);
    			Map<String,Object> map =aCommentDao.findCommentUser(comment.getUserId());
        		if(map!=null && map.size()>0){
        			storeComment.setConntext(comment.getConntext());
        			storeComment.setCreateTime(comment.getCreateTime());
        			storeComment.setNiceName(map.get("niceName").toString());
        			storeComment.setPosition(map.get("position").toString());
        			storeComment.setScore(String.valueOf(comment.getScore()));
        			storeComment.setIcon(map.get("uicon").toString());
        			appcommentList.add(storeComment);
        		}
        		
    		}
    		
    		List list = aCommentDao.findCommentScoreList(appId);
    		Integer count = 0;//评论星级总次数
			String countScore = "0";
			Integer scoreCount = 0;//总分数
			
			String countScores = "0";
			String parten = "#.#";
			DecimalFormat decimal = new DecimalFormat(parten);
			List listScore = new ArrayList();
			
			for(int i=0;i<list.size();i++){
				Map commMap = (Map) list.get(i);
				count+= Integer.valueOf(commMap.get("scoreCount").toString()).intValue();
				scoreCount+= Integer.valueOf(commMap.get("counts").toString()).intValue();
			}
			
			for(int i=0;i<list.size();i++){
				Integer counts = 0;//星级评论总分
				Map comMap = (Map) list.get(i);
				AppScoreVO appScore = new AppScoreVO();
				counts+= Integer.valueOf(comMap.get("scoreCount").toString()).intValue();
				if(count!=0){
					countScore = decimal.format(Float.valueOf(counts)/Float.valueOf(count));//
				}
				appScore.setScore(Integer.valueOf(comMap.get("score").toString()).intValue());
				appScore.setAppScore(countScore);
				listScore.add(appScore);
			}
			if(count>0){
				countScores = decimal.format(Float.valueOf(scoreCount)/Float.valueOf(count));
			}
			
			commentVo.setCommentList(appcommentList);
			commentVo.setCount(count);
			commentVo.setCountScore(countScores);
			commentVo.setScore(listScore);
    		
    	}
		
		return commentVo;
	}

	public List<AppComment> myComment(String appId, String userId) {
		return aCommentDao.myComment(appId, userId);
	}

	@Override
	public CommentVO findAppCommentScoreList(String appId) {
		List<AppComment> commentList =aCommentDao.findComment(appId);
		CommentVO commentVo = new CommentVO();
		
		if(commentList.size()>0){
    		//查询人员信息
    		List<AppStoreCommentVO> appcommentList = new ArrayList<AppStoreCommentVO>();//评论列表
    		for(int i=0;i<commentList.size();i++){
    			AppStoreCommentVO storeComment = new AppStoreCommentVO();
    			AppComment comment = commentList.get(i);
    			Map<String,Object> map =aCommentDao.findAppCommentUser(comment.getUserId());
        		if(map!=null && map.size()>0){
        			storeComment.setConntext(comment.getConntext());
        			storeComment.setCreateTime(comment.getCreateTime());
        			storeComment.setNiceName(map.get("name").toString());
        			storeComment.setScore(String.valueOf(comment.getScore()));
        			storeComment.setIcon(map.get("icon").toString());
        			appcommentList.add(storeComment);
        		}
        		
    		}
    		
    		List list = aCommentDao.findCommentScoreList(appId);
    		Integer count = 0;//评论星级总次数
			String countScore = "0";
			Integer scoreCount = 0;//总分数
			
			String countScores = "0";
			String parten = "#.#";
			DecimalFormat decimal = new DecimalFormat(parten);
			List listScore = new ArrayList();
			
			for(int i=0;i<list.size();i++){
				Map commMap = (Map) list.get(i);
				count+= Integer.valueOf(commMap.get("scoreCount").toString()).intValue();
				scoreCount+= Integer.valueOf(commMap.get("counts").toString()).intValue();
			}
			
			for(int i=0;i<list.size();i++){
				Integer counts = 0;//星级评论总分
				Map comMap = (Map) list.get(i);
				AppScoreVO appScore = new AppScoreVO();
				counts+= Integer.valueOf(comMap.get("scoreCount").toString()).intValue();
				if(count!=0){
					countScore = decimal.format(Float.valueOf(counts)/Float.valueOf(count));//
				}
				appScore.setScore(Integer.valueOf(comMap.get("score").toString()).intValue());
				appScore.setAppScore(countScore);
				listScore.add(appScore);
			}
			if(count>0){
				countScores = decimal.format(Float.valueOf(scoreCount)/Float.valueOf(count));
			}
			
			commentVo.setCommentList(appcommentList);
			commentVo.setCount(count);
			commentVo.setCountScore(countScores);
			commentVo.setScore(listScore);
    		
    	}
		
		return commentVo;
	}

}