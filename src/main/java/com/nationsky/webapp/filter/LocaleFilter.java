package com.nationsky.webapp.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.appfuse.Constants;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nationsky.webapp.util.Utils;

/**
 * Filter to wrap request with a request including user preferred locale.
 */
public class LocaleFilter extends OncePerRequestFilter {

	/**
	 * This method looks for a "locale" request parameter. If it finds one, it
	 * sets it as the preferred locale and also configures it to work with JSTL.
	 * 
	 * @param request the current request
	 * @param response the current response
	 * @param chain the chain
	 * @throws IOException when something goes wrong
	 * @throws ServletException when a communication failure happens
	 */
	@SuppressWarnings("unchecked")
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("进入拦截器...");

		// ============================Session校验Start================================
		/*
		 * 判断本次请求是否需要登陆
		 */
		String url = request.getRequestURI();
		boolean flag = true;
		String URL_FILTER = Utils.getPropertiesValue("URL_FILTER");
//		String URL_FILTER = "/user/login#/user/logout";
		String urlFilterArr[] = URL_FILTER.split("#");
		for (String u : urlFilterArr) {
			String reg = "^(?!.*("+u+")).*$";
			if(!url.matches(reg)){
				flag = false;
				break;
			}
		}
		if (flag) {
			String token = request.getHeader("TOKENID");
			// 校验请求是否有token
			if (token == null || token.length() != 32) {
				System.out.println("token错误");
				response.getWriter().write("-999");
				return;
			}
			ServletContext context = request.getServletContext();
			Map<String, Long> tokenMap = (Map<String, Long>) context.getAttribute("TOKENMAP");
			if (tokenMap == null) {
				tokenMap = new HashMap<String, Long>();
			}
			// 获取上次登录时间
			Long lastTime = tokenMap.get(token);
			if (lastTime == null) {
				response.getWriter().write("-999");
				System.out.println("上次登陆时间为空");
				return;
			}
			// 判断上次登录和本次登陆的时间差
			String timeOut = Utils.getPropertiesValue("SESSION_TIMEOUT");
			if (System.currentTimeMillis() - lastTime > Integer.valueOf(timeOut) * 1000) {
				// 时间差大于30分钟
				response.getWriter().write("-999");
				System.out.println("长时间未操作,登陆超时");
				return;
			}
			// 更改该token的最后访问时间
			tokenMap.put(token, System.currentTimeMillis());
			// 存储SESSION
			context.setAttribute("TOKENMAP", tokenMap);
		}
		// ============================Session校验End================================

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		// 下面那句是核心
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "application/json");
		response.setDateHeader("Expires", 0);

		String locale = request.getParameter("locale");
		Locale preferredLocale = null;

		if (locale != null) {
			int indexOfUnderscore = locale.indexOf('_');
			if (indexOfUnderscore != -1) {
				String language = locale.substring(0, indexOfUnderscore);
				String country = locale.substring(indexOfUnderscore + 1);
				preferredLocale = new Locale(language, country);
			} else {
				preferredLocale = new Locale(locale);
			}
		}

		HttpSession session = request.getSession(false);

		if (session != null) {
			if (preferredLocale == null) {
				preferredLocale = (Locale) session.getAttribute(Constants.PREFERRED_LOCALE_KEY);
			} else {
				session.setAttribute(Constants.PREFERRED_LOCALE_KEY, preferredLocale);
				Config.set(session, Config.FMT_LOCALE, preferredLocale);
			}

			if (preferredLocale != null && !(request instanceof LocaleRequestWrapper)) {
				request = new LocaleRequestWrapper(request, preferredLocale);
				LocaleContextHolder.setLocale(preferredLocale);
			}
		}

		chain.doFilter(request, response);

		// Reset thread-bound LocaleContext.
		LocaleContextHolder.setLocaleContext(null);
	}
}
