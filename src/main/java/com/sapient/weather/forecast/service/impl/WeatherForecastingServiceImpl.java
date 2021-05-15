
package com.sapient.weather.forecast.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.weather.forecast.dto.ListWeatherForecastResponse;
import com.sapient.weather.forecast.dto.WeatherForecastRequest;
import com.sapient.weather.forecast.dto.WeatherForecastResponse;
import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchRequest;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchResponse;
import com.sapient.weather.forecast.fetching.service.WeatherForecastFetchingService;
import com.sapient.weather.forecast.service.WeatherForecastingService;

/**
 * 
 * @author arnidhi
 *
 */
@Service("weatherForecastingServiceImpl")
public class WeatherForecastingServiceImpl implements WeatherForecastingService{
	
	@Autowired
	private WeatherForecastFetchingService weatherForecastFetchingService;

	@Override
	public ListWeatherForecastResponse forecastWeather(WeatherForecastRequest weatherForecastRequest)
			throws ApiException {
		WeatherForecastFetchRequest weatherForecastFetchRequest = new WeatherForecastFetchRequest(weatherForecastRequest);
		List<WeatherForecastFetchResponse> response = weatherForecastFetchingService.fetchForecast(weatherForecastFetchRequest);
		ListWeatherForecastResponse listWeatherForecastResponse = new ListWeatherForecastResponse();
		if(CollectionUtils.isNotEmpty(response)){
			List<WeatherForecastResponse> dayWiseWeatherResponses = new ArrayList<WeatherForecastResponse>(); 
			listWeatherForecastResponse.setWeatherForecastResponses(dayWiseWeatherResponses);
			for(WeatherForecastFetchResponse weatherForecastFetchResponse: response){
				WeatherForecastResponse weatherForecastResponse = WeatherForecastResponse.builder().date(weatherForecastFetchResponse.getDate())
				.maxTemp(weatherForecastFetchResponse.getMaxTemp())
				.minTemp(weatherForecastFetchResponse.getMinTemp())
				.build();
				
				List<String> messages = new ArrayList<>();
				if(weatherForecastResponse.getMaxTemp()>40){
					messages.add("Use sunscreen lotion");
				}
				if(weatherForecastFetchResponse.isRainyDay()){
					messages.add("Carry umbrella");
				}
				weatherForecastResponse.setWeatherMessage(messages);
				dayWiseWeatherResponses.add(weatherForecastResponse);
			}
		}
		return listWeatherForecastResponse;
	}}
