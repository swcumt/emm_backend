package com.nationsky.webapp.util;

import java.util.UUID;

public class Tools {
	
	
	
	//移除"-"号
	
	public static String getuuid(){
		
		StringBuffer temp = new StringBuffer();
		
		String uuid = UUID.randomUUID().toString();
		
		String[] uuiditem  = uuid.split("-");
		
		for(int i = 0;i<uuiditem.length;i++){
			
			temp.append(uuiditem[i]);
			
		}
		
		return temp.toString();
	} 
}
