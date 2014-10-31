package com.nationsky.webapp.controller;

import org.appfuse.dao.SearchException;
import com.nationsky.service.ScheduleListUserManager;
import com.nationsky.model.ScheduleListUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/scheduleListUsers*")
public class ScheduleListUserController {
    private ScheduleListUserManager scheduleListUserManager;

    @Autowired
    public void setScheduleListUserManager(ScheduleListUserManager scheduleListUserManager) {
        this.scheduleListUserManager = scheduleListUserManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Model handleRequest(@RequestParam(required = false, value = "q") String query)
    throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(scheduleListUserManager.search(query, ScheduleListUser.class));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(scheduleListUserManager.getAll());
        }
        return model;
    }
}
