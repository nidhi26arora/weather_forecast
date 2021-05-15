package com.sapient.weather.forecast.fetching.dto;

import com.sapient.weather.forecast.dto.WeatherForecastRequest;

import lombok.Data;

@Data
public class WeatherForecastFetchRequest {
	
	public WeatherForecastFetchRequest(WeatherForecastRequest weatherForecastRequest){
		this.weatherForecastRequest=weatherForecastRequest;
	}
	
	private WeatherForecastRequest weatherForecastRequest;
	
	private Long minFetchTimeInSecs;
	
}
