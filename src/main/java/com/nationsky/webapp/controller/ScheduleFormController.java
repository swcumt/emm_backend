package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.ScheduleManager;
import com.nationsky.model.Schedule;
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
@RequestMapping("/scheduleform*")
public class ScheduleFormController extends BaseFormController {
    private ScheduleManager scheduleManager = null;

    @Autowired
    public void setScheduleManager(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }

    public ScheduleFormController() {
        setCancelView("redirect:schedules");
        setSuccessView("redirect:schedules");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected Schedule showForm(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return scheduleManager.get(new Long(id));
        }

        return new Schedule();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(Schedule schedule, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(schedule, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "scheduleform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = (schedule.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            scheduleManager.remove(schedule.getId());
            saveMessage(request, getText("schedule.deleted", locale));
        } else {
            scheduleManager.save(schedule);
            String key = (isNew) ? "schedule.added" : "schedule.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:scheduleform?id=" + schedule.getId();
            }
        }

        return success;
    }
}
