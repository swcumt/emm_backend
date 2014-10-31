package com.nationsky.webapp.listener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	

	private static  Properties props1 = new Properties();
	
	public static void init(){
		
		InputStream db = null;
		
		try {
			
			db = Config.class.getClassLoader().getResourceAsStream("common.properties");
			
			props1.load(db);
			
			System.out.println("配置推送文件读取成功...");
			
		} catch ( FileNotFoundException e) {
			
			e.printStackTrace();
			
			System.out.println("文件不存在...");
			
		}catch (IOException e) {
			
			e.printStackTrace();
			
			System.out.println("读取文件错误...");
		}
		
	}
	
	public static String getkey(String key){
		
		String value = null;
		
		value = props1.getProperty(key);
		
		return value;
	}
}
