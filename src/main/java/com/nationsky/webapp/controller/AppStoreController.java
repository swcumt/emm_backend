package com.nationsky.webapp.controller;

import org.appfuse.dao.SearchException;
import com.nationsky.service.AppStoreManager;
import com.nationsky.model.AppStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/appStores*")
public class AppStoreController {
    private AppStoreManager appStoreManager;

    @Autowired
    public void setAppStoreManager(AppStoreManager appStoreManager) {
        this.appStoreManager = appStoreManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Model handleRequest(@RequestParam(required = false, value = "q") String query)
    throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(appStoreManager.search(query, AppStore.class));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(appStoreManager.getAll());
        }
        return model;
    }
}
