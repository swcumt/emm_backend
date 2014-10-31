package com.nationsky.app.v1;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.Version;
import com.nationsky.service.VersionManager;
import com.nationsky.webapp.controller.BaseFormController;

/**
 * app版本检测
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/appversions*")
@Scope("prototype")
public class AppVersionsController extends BaseFormController {
    private VersionManager versionManager = null;

    @Autowired
    public void setVersionManager(VersionManager versionManager) {
        this.versionManager = versionManager;
    }
    
    @Autowired
  	private Root root;
    
    /**
     * 最新版本
     * @return
     * @throws ParseException
     */
    @RequestMapping(method=RequestMethod.GET)
   public @ResponseBody Root getMaxVersion() throws ParseException{
	   Version version = versionManager.getMaxVersion();
	   root.setObject(version);
	   return root;
   }
}
