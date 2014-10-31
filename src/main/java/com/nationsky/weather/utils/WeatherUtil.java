package com.nationsky.weather.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 根据天气中文变换图片码制
 * 
 * @author xiaohuiyang
 * 
 */
public class WeatherUtil {

	// 根据城市获取天气信息的java代码
	// cityName 是你要取得天气信息的城市的中文名字，如“北京”，“深圳”
	public static List<OneDayWeatherInf> getWeatherInform(String lng, String lat) {
		// 百度天气API
		StringBuffer strBuf;

		// 要访问的地址URL，通过URLEncoder.encode()函数对于中文进行转码
		String baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location=" + lng + "," + lat + "&output=json&ak=W69oaDTCfuGwzNwmtVvgWfGH";

		strBuf = new StringBuffer();

		try {
			URL url = new URL(baiduUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));// 转码。
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resolveWeatherInf(strBuf.toString());
	}

	public static List<OneDayWeatherInf> resolveWeatherInf(String strPar) {

		JSONObject dataOfJson = JSONObject.fromObject(strPar);

		if (dataOfJson.getInt("error") != 0) {
			return null;
		}

		// 保存全部的天气信息。
		WeatherInf weatherInf = new WeatherInf();

		// 从json数据中取得的时间
		String date = dataOfJson.getString("date");
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8, 10));
		Date today = new Date(year - 1900, month - 1, day);

		JSONArray results = dataOfJson.getJSONArray("results");
		JSONObject results0 = results.getJSONObject(0);

		String currentCity = results0.getString("currentCity");
		int pmTwoPointFive;

		if (results0.getString("pm25").isEmpty()) {
			pmTwoPointFive = 0;
		} else {
			pmTwoPointFive = results0.getInt("pm25");
		}

		try {

			JSONArray index = results0.getJSONArray("index");

			JSONObject index0 = index.getJSONObject(0);// 穿衣
			JSONObject index1 = index.getJSONObject(1);// 洗车
			JSONObject index2 = index.getJSONObject(2);// 感冒
			JSONObject index3 = index.getJSONObject(3);// 运动
			JSONObject index4 = index.getJSONObject(4);// 紫外线强度

			String dressAdvise = index0.getString("des");// 穿衣建议
			String washCarAdvise = index1.getString("des");// 洗车建议
			String coldAdvise = index2.getString("des");// 感冒建议
			String sportsAdvise = index3.getString("des");// 运动建议
			String ultravioletRaysAdvise = index4.getString("des");// 紫外线建议

			weatherInf.setDressAdvise(dressAdvise);
			weatherInf.setWashCarAdvise(washCarAdvise);
			weatherInf.setColdAdvise(coldAdvise);
			weatherInf.setSportsAdvise(sportsAdvise);
			weatherInf.setUltravioletRaysAdvise(ultravioletRaysAdvise);

		} catch (JSONException jsonExp) {

			weatherInf.setDressAdvise("要温度，也要风度。天热缓减衣，天凉及添衣！");
			weatherInf.setWashCarAdvise("你洗还是不洗，灰尘都在哪里，不增不减。");
			weatherInf.setColdAdvise("一天一个苹果，感冒不来找我！多吃水果和蔬菜。");
			weatherInf.setSportsAdvise("生命在于运动！不要总宅在家里哦！");
			weatherInf.setUltravioletRaysAdvise("心灵可以永远年轻，皮肤也一样可以！");
		}

		JSONArray weather_data = results0.getJSONArray("weather_data");// weather_data中有四项
		List<OneDayWeatherInf> weatherList = new ArrayList<OneDayWeatherInf>();
		for(int i=0;i<4;i++){ 
			JSONObject OneDayWeatherinfo = weather_data.getJSONObject(i);
			OneDayWeatherInf oneDayWeatherInf = new OneDayWeatherInf();
			
			String realTemperature = OneDayWeatherinfo.getString("date");
			String week = "";
			String real = "";
			if(realTemperature.contains("实时")){
				try {
					String[] realData = realTemperature.split("实时");
					week = realData[0].split(" ")[0];
					real = realData[1].split(" ")[0].replace("：", "").replace("℃)","");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				week = realTemperature;
			}
			
			oneDayWeatherInf.setWeek(week);
			oneDayWeatherInf.setRealTemperature(real);
			oneDayWeatherInf.setTemperature(OneDayWeatherinfo.getString("temperature").replace("℃", ""));
			oneDayWeatherInf.setWeather(OneDayWeatherinfo.getString("weather"));
			oneDayWeatherInf.setWind(OneDayWeatherinfo.getString("wind"));
			oneDayWeatherInf.setCurrentCity(currentCity);
			oneDayWeatherInf.setDayPictureUrl(OneDayWeatherinfo.getString("dayPictureUrl"));
			oneDayWeatherInf.setPm25(pmTwoPointFive);
			weatherList.add(oneDayWeatherInf);
		}  

		return weatherList;
	}
}
