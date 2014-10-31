package com.nationsky.webapp.controller;

import org.appfuse.dao.SearchException;
import com.nationsky.service.AppstoreEditionManager;
import com.nationsky.model.AppstoreEdition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/appstoreEditions*")
public class AppstoreEditionController {
    private AppstoreEditionManager appstoreEditionManager;

    @Autowired
    public void setAppstoreEditionManager(AppstoreEditionManager appstoreEditionManager) {
        this.appstoreEditionManager = appstoreEditionManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Model handleRequest(@RequestParam(required = false, value = "q") String query)
    throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(appstoreEditionManager.search(query, AppstoreEdition.class));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(appstoreEditionManager.getAll());
        }
        return model;
    }
}
