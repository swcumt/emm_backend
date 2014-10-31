package com.nationsky.app.v1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.AppstoreEdition;
import com.nationsky.service.AppStoreManager;
import com.nationsky.service.AppstoreEditionManager;
import com.nationsky.vo.AppStoreVO;
import com.nationsky.vo.HistoryStoreEdition;
import com.nationsky.webapp.controller.BaseFormController;

/**
 * APP应用
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/appStore*")
@Scope("prototype")
public class AppAppStoresController extends BaseFormController {
    private AppStoreManager appStoreManager = null;

    @Autowired
    public void setAppStoreManager(AppStoreManager appStoreManager) {
        this.appStoreManager = appStoreManager;
    }
    
    private AppstoreEditionManager appstoreEditionManager = null;

	@Autowired
	public void setAppstoreEditionManager(
			AppstoreEditionManager appstoreEditionManager) {
		this.appstoreEditionManager = appstoreEditionManager;
	}
    
    @Autowired
    private Root root;

    
    /**
     * 详细信息
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Root detail(String appId){
    	AppStoreVO appInfo = appStoreManager.appInfo(appId);
    	root.setObject(appInfo);
    	return root;
    }
    
    /**
     * 列表
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/list")
    public @ResponseBody Root list(String modelType){
    	List<AppStoreVO> appStoreList = appStoreManager.appList(modelType);
    	root.setObject(appStoreList);
    	return root;
    }

    /**
     * 获取应用列表
     * @param osId 操作系统ID
     * @param modelType 设备类型
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/getList")
    public @ResponseBody Root list(Long osId,String modelType){
    	List<AppStoreVO> appStoreList = appStoreManager.appList(osId,modelType);
    	root.setObject(appStoreList);
    	return root;
    }
    
    
    /**
     * 历史版本
     * @param appId
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/historyList")
    public @ResponseBody Root historyApp(String appId){
    	List<AppstoreEdition> storeEditionList = appstoreEditionManager.historyApp(appId);
    	List editList = new ArrayList();
    	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	for(int i = 0 ; i< storeEditionList.size();i++){
    		AppstoreEdition storeEdition = storeEditionList.get(i);
    		HistoryStoreEdition historyStoreEdition = new HistoryStoreEdition();
    		historyStoreEdition.setVersionNo(storeEdition.getVersions());
    		historyStoreEdition.setCreateTime(sim.format(storeEdition.getCreateTime()));
    		historyStoreEdition.setDescription(storeEdition.getDescription());
    		editList.add(historyStoreEdition);
    	}
    	root.setObject(editList);
    	return root;
    }
}
