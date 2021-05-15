package com.sapient.weather.forecast.fetching.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchRequest;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchResponse;
import com.sapient.weather.forecast.fetching.service.WeatherForecastFetchingService;
import com.sapient.weather.forecast.properties.WeatherForecast;

@Service("datewiseAcumulateWeatherForecastingService")
public class DatewiseAcumulateWeatherForecastingService implements WeatherForecastFetchingService{

	@Autowired
	@Qualifier("actualWeatherForecastFetchinhService")
	private WeatherForecastFetchingService weatherForecastFetchingService;
	
	@Autowired
	private WeatherForecast weatherForecast;
	
	@Override
	public List<WeatherForecastFetchResponse> fetchForecast(WeatherForecastFetchRequest weatherForecastFetchRequest) throws ApiException {
		List<WeatherForecastFetchResponse> weatherForecastFetchResponses = weatherForecastFetchingService.fetchForecast(weatherForecastFetchRequest);
		weatherForecastFetchResponses = weatherForecastFetchResponses.stream().sorted(Comparator.comparingLong(WeatherForecastFetchResponse::getDate)).collect(Collectors.toList());
		return acumulatedResponse(weatherForecastFetchResponses, weatherForecastFetchRequest.getMinFetchTimeInSecs());
	}
	
	private List<WeatherForecastFetchResponse> acumulatedResponse(List<WeatherForecastFetchResponse> weatherForecastFetchResponses, Long minTime){
		Long curMinTime = minTime;
		Long maxTime = curMinTime + (24 *60*60);
		List<WeatherForecastFetchResponse> actumilatedResponses = new ArrayList<WeatherForecastFetchResponse>();
		WeatherForecastFetchResponse actumilatedResponse = new WeatherForecastFetchResponse();
		for(WeatherForecastFetchResponse weatherForecastFetchResponse: weatherForecastFetchResponses){
			if(weatherForecastFetchResponse.getDate().compareTo(curMinTime)<0){
				continue;
			}
			if(weatherForecastFetchResponse.getDate().compareTo(maxTime)>=0){
				actumilatedResponse.setDate(curMinTime);
				curMinTime = maxTime;
				maxTime = curMinTime + (24 *60*60);
				actumilatedResponses.add(actumilatedResponse);
				if(actumilatedResponses.size()==weatherForecast.getNoOfDaysForecast()){
					break;
				}
				actumilatedResponse = new WeatherForecastFetchResponse();
			}else{
				if(actumilatedResponse.getMinTemp()>weatherForecastFetchResponse.getTemprature()){
					actumilatedResponse.setMinTemp(weatherForecastFetchResponse.getTemprature());
				}
				if(actumilatedResponse.getMaxTemp()<weatherForecastFetchResponse.getTemprature()){
					actumilatedResponse.setMaxTemp(weatherForecastFetchResponse.getTemprature());
				}
				
				actumilatedResponse.setRainyDay(actumilatedResponse.isRainyDay() || weatherForecastFetchResponse.isRainyDay());
			}
		}
		return actumilatedResponses;
	}

}
