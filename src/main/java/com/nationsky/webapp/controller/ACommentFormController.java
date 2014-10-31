package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.AppCommentManager;
import com.nationsky.model.AppComment;
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
@RequestMapping("/aCommentform*")
public class ACommentFormController extends BaseFormController {
    private AppCommentManager aCommentManager = null;

    @Autowired
    public void setACommentManager(AppCommentManager aCommentManager) {
        this.aCommentManager = aCommentManager;
    }

    public ACommentFormController() {
        setCancelView("redirect:aComments");
        setSuccessView("redirect:aComments");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected AppComment showForm(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return aCommentManager.get(new String(id));
        }

        return new AppComment();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(AppComment aComment, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(aComment, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "aCommentform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        boolean isNew = (aComment.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            aCommentManager.remove(aComment.getId());
            saveMessage(request, getText("aComment.deleted", locale));
        } else {
            aCommentManager.save(aComment);
            String key = (isNew) ? "aComment.added" : "aComment.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:aCommentform?id=" + aComment.getId();
            }
        }

        return success;
    }
}
