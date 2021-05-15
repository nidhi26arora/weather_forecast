package com.sapient.weather.forecast.dto;

import java.util.List;

import lombok.Data;

/**
 * List of weather forecast
 * 
 * @author arnidhi
 * @version 1.0.0
 */
@Data
public class ListWeatherForecastResponse {
	
	List<WeatherForecastResponse> weatherForecastResponses;

}
