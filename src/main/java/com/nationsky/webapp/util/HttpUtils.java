package com.nationsky.webapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nationsky.vo.AppVO;

public class HttpUtils {

	public static AppVO appdetail(String appId){
//		trustAllHosts();
		String urls = Utils.getPropertiesValue("appdetailurl")+"?id="+appId;
		URL url;
		String total = ""; 
		AppVO app = new AppVO();
		try {
			url = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setDoOutput(true); 
			connection.setDoInput(true);//打开输入，从服务器读取返回数据
			connection.setRequestMethod("POST"); //设置登录模式为POST（字符串大写）
			connection.setInstanceFollowRedirects(false); 
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");     
			 out.write("id="+appId+"");
		     out.flush();     
		     out.close();
			InputStream urlStream = connection.getInputStream();  
			BufferedReader bufferedReader = new BufferedReader(  
			      new InputStreamReader(urlStream,"UTF-8"));  
			String ss = null;  
			while ((ss = bufferedReader.readLine()) != null) {  
				total += ss;  
			}  
			bufferedReader.close(); 
			System.out.println(total);
			
			JSONObject jsonObject = JSONObject.parseObject(total);
			
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("results").toString());
			
			for(int i=0;i<jsonArray.size();i++){
				
				JSONObject jobject = jsonArray.getJSONObject(i);
				
				app.setBundleId(jobject.get("bundleId").toString());		
				app.setTrackName(jobject.get("trackName").toString());	
				app.setSellerName(jobject.get("sellerName").toString());	
				app.setArtworkUrl60(jobject.get("artworkUrl60").toString());	
				app.setDescription(jobject.get("description").toString());	
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return app;
	}
}
