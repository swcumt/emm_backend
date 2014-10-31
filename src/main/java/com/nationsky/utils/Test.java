package com.nationsky.utils;

import java.util.Date;

public class Test {
	public static void main(String[] args) {

		String url = "aa#bb#cc#dd#ee#ff";
		String str[] = url.split("#");
		boolean bl = false;
		for (String u : str) {
			String reg = "^(?!.*("+u+")).*$";// 用到了前瞻
			if(!"fefeeafefefe".matches(reg)){
				bl = true;
				break;
			}
		}
		System.out.println(bl);
//		
//		String reg = "^(?!.*(不合谐)).*$";// 用到了前瞻
//		
//		System.out.println("不管信不信,反正现在很不合谐".matches(reg));// false不通过
//		System.out.println("不管信不信,反正现在非常合谐".matches(reg));// true通过
//		System.out.println("不合谐在某国是普遍存在的".matches(reg));// false不通过
//		
//		String reg1="^.*(?<!(不合谐))$";//用到了后顾  
//        System.out.println("不管信不信,反正现在很不合谐".matches(reg1));//false不通过  
//        System.out.println("不管信不信,反正现在非常合谐".matches(reg1));//true通过  
//        System.out.println("不合谐在某国是普遍存在的".matches(reg1));//true通

	}
}
