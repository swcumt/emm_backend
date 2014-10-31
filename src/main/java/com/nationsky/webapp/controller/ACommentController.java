package com.nationsky.webapp.controller;

import org.appfuse.dao.SearchException;
import com.nationsky.service.AppCommentManager;
import com.nationsky.model.AppComment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/aComments*")
public class ACommentController {
    private AppCommentManager aCommentManager;

    @Autowired
    public void setACommentManager(AppCommentManager aCommentManager) {
        this.aCommentManager = aCommentManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Model handleRequest(@RequestParam(required = false, value = "q") String query)
    throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(aCommentManager.search(query, AppComment.class));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(aCommentManager.getAll());
        }
        return model;
    }
}
