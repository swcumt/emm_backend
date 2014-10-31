package com.nationsky.api.v1;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.FullTrialCode;
import com.nationsky.service.FullTrialCodeManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
/**
 * 码表管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/v1/fullTrialform*")
@Scope("prototype")
public class ApiFullTrialController extends BaseFormController {
    private FullTrialCodeManager fullTrialCodeManager = null;

    @Autowired
    public void setFullTrialCodeManager(FullTrialCodeManager fullTrialCodeManager) {
        this.fullTrialCodeManager = fullTrialCodeManager;
    }
    
    @Autowired
	private Root root;
    
	@RequestMapping(value = "/isExistsText",method = RequestMethod.POST)
	public @ResponseBody
	Root isExistsText(FullTrialCode codeOs) {
		if(fullTrialCodeManager.exists(codeOs)){
			root.setCode(-1);
		}
		return root;
	}
    
    /**
     * 列表
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/list")
    public @ResponseBody Root list(String pageSize){
    	Page page = fullTrialCodeManager.getfindFullTrialCode(pageSize);
    	root.setObject(page.getObjList());
    	root.setPageCount(page.getCountPage());
    	return root;
    }

    /**
     * 添加
     * @param fullTrialCode
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Root save(FullTrialCode fullTrialCode) {
		if (fullTrialCodeManager.exists(fullTrialCode)) {
			root.setMessage(2, "此名称已存在");
		} else {
			FullTrialCode os = fullTrialCodeManager.save(fullTrialCode);
			if (os == null) {
				root.setMessage(1, "保存失败");
			} else {
				if (fullTrialCode.getId() != null && !"".equals(fullTrialCode.getId())) {
					root.setMessage("修改成功");
				} else {
					root.setMessage("添加成功");
				}
			}
		}
		return root;
	}
    
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
    public @ResponseBody Root delete(@PathVariable String id){
    	//判断参数id是否为空
    	if (!StringUtils.isBlank(id)) {
    		try {
    			fullTrialCodeManager.remove(new Long(id));
    			 root.setMessage(0, "删除成功");
			} catch (Exception e) {
				 root.setError("数据不存在");
			}
       }
		return root;
    }
    
    /**
     * 查看详细
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/{id}")
    public @ResponseBody Root detail(@PathVariable String id){
    	FullTrialCode fullTrialCode = null;
    	//判断参数id是否为空
    	if (!StringUtils.isBlank(id)) {
    		try {
    			fullTrialCode = fullTrialCodeManager.get(new Long(id));
			} catch (Exception e) {
				root.setError("数据不存在");
			}
    	}
    	root.setObject(fullTrialCode);
		return root;
    }
    
}
