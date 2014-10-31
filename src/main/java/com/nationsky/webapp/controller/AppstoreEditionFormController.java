package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.AppstoreEditionManager;
import com.nationsky.model.AppstoreEdition;
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
@RequestMapping("/appstoreEditionform*")
public class AppstoreEditionFormController extends BaseFormController {
    private AppstoreEditionManager appstoreEditionManager = null;

    @Autowired
    public void setAppstoreEditionManager(AppstoreEditionManager appstoreEditionManager) {
        this.appstoreEditionManager = appstoreEditionManager;
    }

    public AppstoreEditionFormController() {
        setCancelView("redirect:appstoreEditions");
        setSuccessView("redirect:appstoreEditions");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected AppstoreEdition showForm(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return appstoreEditionManager.get(new Long(id));
        }

        return new AppstoreEdition();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(AppstoreEdition appstoreEdition, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(appstoreEdition, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "appstoreEditionform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = (appstoreEdition.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            appstoreEditionManager.remove(appstoreEdition.getId());
            saveMessage(request, getText("appstoreEdition.deleted", locale));
        } else {
            appstoreEditionManager.save(appstoreEdition);
            String key = (isNew) ? "appstoreEdition.added" : "appstoreEdition.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:appstoreEditionform?id=" + appstoreEdition.getId();
            }
        }

        return success;
    }
}
