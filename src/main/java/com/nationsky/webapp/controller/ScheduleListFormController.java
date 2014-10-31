package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.ScheduleListManager;
import com.nationsky.model.ScheduleList;
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
@RequestMapping("/scheduleListform*")
public class ScheduleListFormController extends BaseFormController {
    private ScheduleListManager scheduleListManager = null;

    @Autowired
    public void setScheduleListManager(ScheduleListManager scheduleListManager) {
        this.scheduleListManager = scheduleListManager;
    }

    public ScheduleListFormController() {
        setCancelView("redirect:scheduleLists");
        setSuccessView("redirect:scheduleLists");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected ScheduleList showForm(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return scheduleListManager.get(new Long(id));
        }

        return new ScheduleList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(ScheduleList scheduleList, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(scheduleList, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "scheduleListform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = (scheduleList.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            scheduleListManager.remove(scheduleList.getId());
            saveMessage(request, getText("scheduleList.deleted", locale));
        } else {
            scheduleListManager.save(scheduleList);
            String key = (isNew) ? "scheduleList.added" : "scheduleList.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:scheduleListform?id=" + scheduleList.getId();
            }
        }

        return success;
    }
}
