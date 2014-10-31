package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.ScheduleUserManager;
import com.nationsky.model.ScheduleUser;
import com.nationsky.webapp.controller.BaseFormController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping("/scheduleUserform*")
public class ScheduleUserFormController extends BaseFormController {
    private ScheduleUserManager scheduleUserManager = null;

    @Autowired
    public void setScheduleUserManager(ScheduleUserManager scheduleUserManager) {
        this.scheduleUserManager = scheduleUserManager;
    }

    public ScheduleUserFormController() {
        setCancelView("redirect:scheduleUsers");
        setSuccessView("redirect:scheduleUsers");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected ScheduleUser showForm(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return scheduleUserManager.get(new Long(id));
        }

        return new ScheduleUser();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(ScheduleUser scheduleUser, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(scheduleUser, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "scheduleUserform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = (scheduleUser.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            scheduleUserManager.remove(scheduleUser.getId());
            saveMessage(request, getText("scheduleUser.deleted", locale));
        } else {
            scheduleUserManager.save(scheduleUser);
            String key = (isNew) ? "scheduleUser.added" : "scheduleUser.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:scheduleUserform?id=" + scheduleUser.getId();
            }
        }

        return success;
    }
}
