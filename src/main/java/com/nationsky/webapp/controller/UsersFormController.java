package com.nationsky.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.model.Users;
import com.nationsky.service.UsersManager;

@Controller
@RequestMapping("/usersform")
public class UsersFormController extends BaseFormController {
	private UsersManager usersManager = null;

	@Autowired
	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	public UsersFormController() {
		setCancelView("redirect:userss");
		setSuccessView("redirect:userss");
	}

//	@ModelAttribute
//	@RequestMapping(method = RequestMethod.GET)
//	protected Users showForm(HttpServletRequest request) throws Exception {
//		String id = request.getParameter("id");
//
//		if (!StringUtils.isBlank(id)) {
//			return usersManager.get(new Long(id));
//		}
//
//		return new Users();
//	}
//
//	@RequestMapping(method = RequestMethod.POST)
//	public String onSubmit(Users users, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		if (request.getParameter("cancel") != null) {
//			return getCancelView();
//		}
//
//		if (validator != null) { // validator is null during testing
//			validator.validate(users, errors);
//
//			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't
//																				// validate
//																				// when
//																				// deleting
//				return "usersform";
//			}
//		}
//
//		log.debug("entering 'onSubmit' method...");
//
//		boolean isNew = (users.getId() == null);
//		String success = getSuccessView();
//		Locale locale = request.getLocale();
//
//		if (request.getParameter("delete") != null) {
//			usersManager.remove(users.getId());
//			saveMessage(request, getText("users.deleted", locale));
//		} else {
//			usersManager.save(users);
//			String key = (isNew) ? "users.added" : "users.updated";
//			saveMessage(request, getText(key, locale));
//
//			if (!isNew) {
//				success = "redirect:usersform?id=" + users.getId();
//			}
//		}
//		outJson(response);
//		return null;
//	}

	/**
	 * 管理员登录
	 */
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public @ResponseBody  Users userLogin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("into userLogin");
		Users user = new Users();
		user.setFlag(1L);
		user.setId(11L);
		user.setName("zhangsan");
		user.setUsername("123");
		user.setPassword("456");
		return user;
	}

}
