package com.nationsky.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.nationsky.service.FullTrialCodeManager;
import com.nationsky.model.FullTrialCode;
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
@RequestMapping("/fullTrialCodeform*")
public class FullTrialCodeFormController extends BaseFormController {
	private FullTrialCodeManager fullTrialCodeManager = null;

	@Autowired
	public void setFullTrialCodeManager(FullTrialCodeManager fullTrialCodeManager) {
		this.fullTrialCodeManager = fullTrialCodeManager;
	}

	public FullTrialCodeFormController() {
		setCancelView("redirect:fullTrialCodes");
		setSuccessView("redirect:fullTrialCodes");
	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	protected FullTrialCode showForm(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");

		if (!StringUtils.isBlank(id)) {
			return fullTrialCodeManager.get(new Long(id));
		}

		return new FullTrialCode();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(FullTrialCode fullTrialCode, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return getCancelView();
		}

		if (validator != null) { // validator is null during testing
			validator.validate(fullTrialCode, errors);

			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't
																				// validate
																				// when
																				// deleting
				return "fullTrialCodeform";
			}
		}

		log.debug("entering 'onSubmit' method...");

		boolean isNew = (fullTrialCode.getId() == null);
		String success = getSuccessView();
		Locale locale = request.getLocale();

		if (request.getParameter("delete") != null) {
			fullTrialCodeManager.remove(fullTrialCode.getId());
			saveMessage(request, getText("fullTrialCode.deleted", locale));
		} else {
			fullTrialCodeManager.save(fullTrialCode);
			String key = (isNew) ? "fullTrialCode.added" : "fullTrialCode.updated";
			saveMessage(request, getText(key, locale));

			if (!isNew) {
				success = "redirect:fullTrialCodeform?id=" + fullTrialCode.getId();
			}
		}

		return success;
	}
}
