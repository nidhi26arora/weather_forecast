package com.sapient.weather.forecast.service;

import com.sapient.weather.forecast.dto.ListWeatherForecastResponse;
import com.sapient.weather.forecast.dto.WeatherForecastRequest;
import com.sapient.weather.forecast.exception.ApiException;

/**
 * Weather forecasting service
 * @author arnidhi
 *
 */
public interface WeatherForecastingService {
	
	
	/**
	 * Fetch weather forecast for set number of days
	 * @param weatherForecastRequest The weather forecast request object
	 * @return the list of weather forecast responses 
	 * @throws ApiException
	 */
	ListWeatherForecastResponse forecastWeather(WeatherForecastRequest weatherForecastRequest) throws ApiException;

}
