package com.sapient.weather.forecast.fetching.service;

import java.util.List;

import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchRequest;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchResponse;

public interface WeatherForeCastFetcher {
	
	List<WeatherForecastFetchResponse>fetchWeatherForecasting(WeatherForecastFetchRequest weatherForecastFetchRequest) throws ApiException;
	
	boolean isEnabled(WeatherForecastFetchRequest weatherForecastFetchRequest);

}
