package com.nationsky.webapp.controller;

import org.appfuse.dao.SearchException;
import com.nationsky.service.UsersManager;
import com.nationsky.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/userss*")
public class UsersController {
    private UsersManager usersManager;

    @Autowired
    public void setUsersManager(UsersManager usersManager) {
        this.usersManager = usersManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Model handleRequest(@RequestParam(required = false, value = "q") String query)
    throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(usersManager.search(query, Users.class));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(usersManager.getAll());
        }
        return model;
    }
}
