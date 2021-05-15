package com.sapient.weather.forecast.fetching.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchRequest;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchResponse;
import com.sapient.weather.forecast.fetching.service.WeatherForeCastFetcher;
import com.sapient.weather.forecast.fetching.service.WeatherForecastFetchingService;

@Service("actualWeatherForecastFetchinhService")
public class ActualWeatherForecastFetchinhService implements WeatherForecastFetchingService{
	
	@Autowired
	private List<WeatherForeCastFetcher> weatherForeCastFetchers;

	@Override
	public List<WeatherForecastFetchResponse> fetchForecast(WeatherForecastFetchRequest weatherForecastFetchRequest) throws ApiException {
		for(WeatherForeCastFetcher weatherForeCastFetcher : weatherForeCastFetchers){
			if(weatherForeCastFetcher.isEnabled(weatherForecastFetchRequest)){
				return weatherForeCastFetcher.fetchWeatherForecasting(weatherForecastFetchRequest);
			}
		}
		return new ArrayList<WeatherForecastFetchResponse>();
	}
	
	

}
