package com.nationsky.app.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.AppComment;
import com.nationsky.service.AppCommentManager;
import com.nationsky.service.AppStoreManager;
import com.nationsky.vo.AppStoreVO;
import com.nationsky.vo.CommentVO;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;

/**
 * app 应用评论
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/appcomment*")
@Scope("prototype")
public class AppCommentsController extends BaseFormController {
    private AppCommentManager appCommentManager = null;

    @Autowired
    public void setAppCommentManager(AppCommentManager appCommentManager) {
        this.appCommentManager = appCommentManager;
    }
    
    private AppStoreManager appStoreManager = null;

    @Autowired
    public void setAppStoreManager(AppStoreManager appStoreManager) {
        this.appStoreManager = appStoreManager;
    }
    
    @Autowired
    private Root root;

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Root add(AppComment comments){
    	//判断是否已经评论
    	AppComment appcomment = appCommentManager.findComment(comments.getVersionId(), comments.getUserId());
    	if(appcomment != null){
    		appcomment.setFlag(Long.valueOf(0));
    		 appCommentManager.save(appcomment);
    	}
    	comments.setCreateTime(Utils.getCurrentTime());
    	comments.setFlag(Long.valueOf(1));
    	comments.setId(Utils.getUUID());
    	try {
    		AppComment appComment = appCommentManager.save(comments);
        	if(appComment == null){
        		root.setMessage(1, "添加失败");
        	}else{
        		root.setMessage("添加成功");
        		root.setObject(appComment);
        	}
		} catch (Exception e) {
			root.setMessage(1, "添加失败");
			e.printStackTrace();
		}
    	
    	return root;
    }
    
    /**
     * 客户端评论列表
     * @param id
     * @return
     * @throws Exception 
     */
    @RequestMapping(method=RequestMethod.POST,value="/appcomment")
    public @ResponseBody Root appcomment(String versionId) throws Exception{
			CommentVO commentVo = appCommentManager.findCommentScoreList(versionId);
    		root.setObject(commentVo);
    		return root;
    }
    
    /**
     * 客户端评论列表(应用用户)
     * @param id
     * @return
     * @throws Exception 
     */
    @RequestMapping(method=RequestMethod.POST,value="/list")
    public @ResponseBody Root appCommentList(String versionId) throws Exception{
    	CommentVO commentVo = appCommentManager.findAppCommentScoreList(versionId);
    	root.setObject(commentVo);
    	return root;
    }
    
    /**
     * 我的评分
     * @param appId
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="/mycomment")
    public @ResponseBody Root myComment(String versionId,String userId){
    	AppStoreVO appInfo = appStoreManager.appInfo(versionId);
    	if(appInfo != null){
    		List<AppComment> commentList = appCommentManager.myComment(versionId, userId);
        	root.setObject(commentList);
    	}else{
    		root.setMessage(1, "应用不存在");
    	}
    	
    	return root;
    }
}
