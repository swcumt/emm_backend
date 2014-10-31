package com.nationsky.webapp.controller;

import org.appfuse.dao.SearchException;
import com.nationsky.service.FullTrialCodeManager;
import com.nationsky.model.FullTrialCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fullTrialCodes*")
public class FullTrialCodeController {
    private FullTrialCodeManager fullTrialCodeManager;

    @Autowired
    public void setFullTrialCodeManager(FullTrialCodeManager fullTrialCodeManager) {
        this.fullTrialCodeManager = fullTrialCodeManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Model handleRequest(@RequestParam(required = false, value = "q") String query)
    throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(fullTrialCodeManager.search(query, FullTrialCode.class));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(fullTrialCodeManager.getAll());
        }
        return model;
    }
}
