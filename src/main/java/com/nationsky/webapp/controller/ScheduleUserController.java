package com.nationsky.webapp.controller;

import org.appfuse.dao.SearchException;
import com.nationsky.service.ScheduleUserManager;
import com.nationsky.model.ScheduleUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/scheduleUsers*")
public class ScheduleUserController {
    private ScheduleUserManager scheduleUserManager;

    @Autowired
    public void setScheduleUserManager(ScheduleUserManager scheduleUserManager) {
        this.scheduleUserManager = scheduleUserManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Model handleRequest(@RequestParam(required = false, value = "q") String query)
    throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(scheduleUserManager.search(query, ScheduleUser.class));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(scheduleUserManager.getAll());
        }
        return model;
    }
}
