package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.ScheduleListUserManager;
import com.nationsky.model.ScheduleListUser;
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
@RequestMapping("/scheduleListUserform*")
public class ScheduleListUserFormController extends BaseFormController {
    private ScheduleListUserManager scheduleListUserManager = null;

    @Autowired
    public void setScheduleListUserManager(ScheduleListUserManager scheduleListUserManager) {
        this.scheduleListUserManager = scheduleListUserManager;
    }

    public ScheduleListUserFormController() {
        setCancelView("redirect:scheduleListUsers");
        setSuccessView("redirect:scheduleListUsers");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected ScheduleListUser showForm(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return scheduleListUserManager.get(new Long(id));
        }

        return new ScheduleListUser();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(ScheduleListUser scheduleListUser, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(scheduleListUser, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "scheduleListUserform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = (scheduleListUser.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            scheduleListUserManager.remove(scheduleListUser.getId());
            saveMessage(request, getText("scheduleListUser.deleted", locale));
        } else {
            scheduleListUserManager.save(scheduleListUser);
            String key = (isNew) ? "scheduleListUser.added" : "scheduleListUser.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:scheduleListUserform?id=" + scheduleListUser.getId();
            }
        }

        return success;
    }
}
