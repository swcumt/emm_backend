package com.nationsky.webapp.util;

import java.util.List;

import com.nationsky.model.VendorDevice;
import com.nationsky.webapp.listener.Config;

public class PushUtil {
	
	/**
	 * 推送消息
	 * @param json
	 * @param venList
	 */
	public static void pushServer(String json,List<VendorDevice> venList){
		String url = Config.getkey("url");
		for (int i = 0; i < venList.size(); i++) {
			VendorDevice ven = venList.get(i);
			HttpPost.sendGet(url, "type=1&clientid="+ ven.getDeviceid() + "&uuid="+ Tools.getuuid() + "&content=" + json);
		}
	}
	/**
	 * 推送消息
	 * @param json		推送内容
	 * @param deveceId	设备id(用户id)
	 */
	public static void pushServer(String json,String deveceId){
		String url = Config.getkey("url");
		HttpPost.sendGet(url, "type=1&clientid="+ deveceId + "&uuid="+ Tools.getuuid() + "&content=" + json);
	}
	
	
}
