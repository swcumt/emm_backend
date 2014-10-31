package com.nationsky.webapp.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class AdLogin {

	/**
	 * java验证AD域用户登录 传入用户名(userName)和密码(password)进行验证，验证成功返回用户名
	 */

	public static String checkMail(String userName,String password) {
		String host = Utils.getPropertiesValue("host").toString(); // AD服务器IP
		String port = Utils.getPropertiesValue("port").toString(); // 端口
		String domain = Utils.getPropertiesValue("domain").toString(); //邮箱的后缀名
//		String user="domain\\user";// 这里有两种格式，domain\User或邮箱的后缀名,建议用domain\User这种格式
		String url = new String("ldap://" + host + ":" + port);
		String user = userName.indexOf(domain) > 0 ? userName : userName +domain;
		Hashtable env = new Hashtable();
		DirContext ctx;
		env.put(Context.SECURITY_AUTHENTICATION, "simple");// 一种模式，不用管，就这么写就可以了
		env.put(Context.SECURITY_PRINCIPAL, user); 
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.INITIAL_CONTEXT_FACTORY,
		"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		try {
			ctx = new InitialDirContext(env);
			ctx.close();
			return userName; // 验证成功返回name
		} catch (NamingException err) {
			err.printStackTrace();
			return "验证失败";// 验证失败返回空
		}
	}
	
}
