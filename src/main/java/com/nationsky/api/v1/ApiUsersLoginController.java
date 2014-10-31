package com.nationsky.api.v1;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.Users;
import com.nationsky.service.UsersManager;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;

/**
 * 用户登录
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/v1/user")
@Scope("prototype")
public class ApiUsersLoginController extends BaseFormController {
	private UsersManager usersManager = null;

	@Autowired
	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	@Autowired
	private Root root;

	/**
	 * 管理员登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Root userLogin(Users user, HttpServletRequest request, HttpServletResponse response) {
		Users u = usersManager.findUsers(user.getUsername(), user.getPassword());
		if (u == null) {
			root.setMessage(1, "用户名或密码错误");
		} else {
			ServletContext context = request.getSession().getServletContext();
			//存储用户登陆信息,KEY=Token,VALUE=User
			Map<String, Users> userMap = (Map<String, Users>) context.getAttribute("USERMAP");
			//存储用户登陆时间,KEY=Token,VALUE=上次访问时间
			Map<String, Long> tokenMap = (Map<String, Long>) context.getAttribute("TOKENMAP");
			if (tokenMap == null) {
				tokenMap = new HashMap<String, Long>();
				userMap = new HashMap<String, Users>();
			}
			String token = Utils.getUUID().toUpperCase();
			tokenMap.put(token, System.currentTimeMillis());
			userMap.put(token, u);
			context.setAttribute("TOKENMAP", tokenMap);
			context.setAttribute("USERMAP", userMap);
			root.setMessage(0, token);
			root.setObject(u);
			response.setHeader("TOKENID", token);
		}
		return root;
	}

	/**
	 * 管理员注销
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody Root userLogout(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("TOKENID");
		ServletContext context = request.getSession().getServletContext();
		Map<String, Long> tokenMap = (Map<String, Long>) context.getAttribute("TOKENMAP");
		if (tokenMap == null) {
			tokenMap = new HashMap<String, Long>();
		}
		tokenMap.remove(token);
		context.setAttribute("TOKENMAP", tokenMap);
		root.setMessage(0, "");
		return root;
	}

}
