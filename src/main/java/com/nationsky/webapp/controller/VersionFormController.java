package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.VersionManager;
import com.nationsky.model.Version;
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
@RequestMapping("/versionform*")
public class VersionFormController extends BaseFormController {
    private VersionManager versionManager = null;

    @Autowired
    public void setVersionManager(VersionManager versionManager) {
        this.versionManager = versionManager;
    }

    public VersionFormController() {
        setCancelView("redirect:versions");
        setSuccessView("redirect:versions");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected Version showForm(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return versionManager.get(new Long(id));
        }

        return new Version();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(Version version, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(version, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "versionform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = (version.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            versionManager.remove(version.getId());
            saveMessage(request, getText("version.deleted", locale));
        } else {
            versionManager.save(version);
            String key = (isNew) ? "version.added" : "version.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:versionform?id=" + version.getId();
            }
        }

        return success;
    }
}
