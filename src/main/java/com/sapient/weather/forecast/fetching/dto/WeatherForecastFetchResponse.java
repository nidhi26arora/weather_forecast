package com.sapient.weather.forecast.fetching.dto;

import lombok.Data;

@Data
public class WeatherForecastFetchResponse {
	
	Long date;
	
	Float temprature;
	
	float minTemp = Float.MAX_VALUE;
	
	float maxTemp = Float.MIN_VALUE;
	
	boolean isRainyDay; 

}
