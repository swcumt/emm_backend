package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.AppStoreManager;
import com.nationsky.model.AppStore;
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
@RequestMapping("/appStoreform*")
public class AppStoreFormController extends BaseFormController {
    private AppStoreManager appStoreManager = null;

    @Autowired
    public void setAppStoreManager(AppStoreManager appStoreManager) {
        this.appStoreManager = appStoreManager;
    }

    public AppStoreFormController() {
        setCancelView("redirect:appStores");
        setSuccessView("redirect:appStores");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected AppStore showForm(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return appStoreManager.get(new Long(id));
        }

        return new AppStore();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(AppStore appStore, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(appStore, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "appStoreform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = (appStore.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            appStoreManager.remove(appStore.getId());
            saveMessage(request, getText("appStore.deleted", locale));
        } else {
            appStoreManager.save(appStore);
            String key = (isNew) ? "appStore.added" : "appStore.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:appStoreform?id=" + appStore.getId();
            }
        }

        return success;
    }
}
