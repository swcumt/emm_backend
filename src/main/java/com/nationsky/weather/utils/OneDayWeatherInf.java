package com.nationsky.weather.utils;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.search.annotations.Indexed;

import com.nationsky.entity.BaseEntity;

@SuppressWarnings("serial")
@Indexed
@XmlRootElement
public class OneDayWeatherInf extends BaseEntity implements Serializable{
	private String currentCity;
	private Integer pm25;
	private String dayPictureUrl;
	private String weather;
	private String wind;
	private String temperature;
	private String week;
	private String realTemperature;
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getDayPictureUrl() {
		return dayPictureUrl;
	}
	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public Integer getPm25() {
		return pm25;
	}
	public void setPm25(Integer pm25) {
		this.pm25 = pm25;
	}
	
	public String getRealTemperature() {
		return realTemperature;
	}
	public void setRealTemperature(String realTemperature) {
		this.realTemperature = realTemperature;
	}
	@Override
	public String toString() {
		return null;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentCity == null) ? 0 : currentCity.hashCode());
		result = prime * result + ((dayPictureUrl == null) ? 0 : dayPictureUrl.hashCode());
		result = prime * result + ((pm25 == null) ? 0 : pm25.hashCode());
		result = prime * result + ((realTemperature == null) ? 0 : realTemperature.hashCode());
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
		result = prime * result + ((weather == null) ? 0 : weather.hashCode());
		result = prime * result + ((week == null) ? 0 : week.hashCode());
		result = prime * result + ((wind == null) ? 0 : wind.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OneDayWeatherInf other = (OneDayWeatherInf) obj;
		if (currentCity == null) {
			if (other.currentCity != null)
				return false;
		} else if (!currentCity.equals(other.currentCity))
			return false;
		if (dayPictureUrl == null) {
			if (other.dayPictureUrl != null)
				return false;
		} else if (!dayPictureUrl.equals(other.dayPictureUrl))
			return false;
		if (pm25 == null) {
			if (other.pm25 != null)
				return false;
		} else if (!pm25.equals(other.pm25))
			return false;
		if (realTemperature == null) {
			if (other.realTemperature != null)
				return false;
		} else if (!realTemperature.equals(other.realTemperature))
			return false;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		if (weather == null) {
			if (other.weather != null)
				return false;
		} else if (!weather.equals(other.weather))
			return false;
		if (week == null) {
			if (other.week != null)
				return false;
		} else if (!week.equals(other.week))
			return false;
		if (wind == null) {
			if (other.wind != null)
				return false;
		} else if (!wind.equals(other.wind))
			return false;
		return true;
	}
	

}
