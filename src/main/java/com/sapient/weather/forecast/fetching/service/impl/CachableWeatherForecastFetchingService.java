package com.sapient.weather.forecast.fetching.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchRequest;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchResponse;
import com.sapient.weather.forecast.fetching.service.WeatherForecastFetchingService;
import com.sapient.weather.forecast.util.WeatherForecastConstant;

@Service("cachableWeatherForecastFetchingService")
public class CachableWeatherForecastFetchingService implements WeatherForecastFetchingService{

	@Autowired
	@Qualifier("datewiseAcumulateWeatherForecastingService")
	private WeatherForecastFetchingService weatherForecastFetchingService;
	
	@Override
	@Cacheable(value=WeatherForecastConstant.FORECAST_CACHE)
	public List<WeatherForecastFetchResponse> fetchForecast(WeatherForecastFetchRequest weatherForecastFetchRequest) throws ApiException {
		return weatherForecastFetchingService.fetchForecast(weatherForecastFetchRequest);
	}

}
