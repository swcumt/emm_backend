package com.nationsky.app.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.weather.utils.OneDayWeatherInf;
import com.nationsky.weather.utils.WeatherUtil;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/weather")
@Scope("prototype")
public class AppWeatherController  extends BaseFormController{

	@Autowired
	private Root root;
	
	/**
     * 获取天气
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Root getWeather(String lng,String lat){
    	List<OneDayWeatherInf> weatherInfList = WeatherUtil.getWeatherInform(lng,lat);
    	root.setObject(weatherInfList);
		return root;
    }
}
