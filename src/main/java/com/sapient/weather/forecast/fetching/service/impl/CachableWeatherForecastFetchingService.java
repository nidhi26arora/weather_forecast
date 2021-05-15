package com.sapient.weather.forecast.fetching.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchRequest;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchResponse;
import com.sapient.weather.forecast.fetching.service.WeatherForecastFetchingService;

@Service("cachableWeatherForecastFetchingService")
public class CachableWeatherForecastFetchingService implements WeatherForecastFetchingService{

	@Autowired
	@Qualifier("datewiseAcumulateWeatherForecastingService")
	private WeatherForecastFetchingService weatherForecastFetchingService;
	
	@Override
	public List<WeatherForecastFetchResponse> fetchForecast(WeatherForecastFetchRequest weatherForecastFetchRequest) throws ApiException {
		return weatherForecastFetchingService.fetchForecast(weatherForecastFetchRequest);
	}

}
