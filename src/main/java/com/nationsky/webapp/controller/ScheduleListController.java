package com.nationsky.webapp.controller;

import org.appfuse.dao.SearchException;
import com.nationsky.service.ScheduleListManager;
import com.nationsky.model.ScheduleList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/scheduleLists*")
public class ScheduleListController {
    private ScheduleListManager scheduleListManager;

    @Autowired
    public void setScheduleListManager(ScheduleListManager scheduleListManager) {
        this.scheduleListManager = scheduleListManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Model handleRequest(@RequestParam(required = false, value = "q") String query)
    throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(scheduleListManager.search(query, ScheduleList.class));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(scheduleListManager.getAll());
        }
        return model;
    }
}
