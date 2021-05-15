package com.sapient.weather.forecast.dto;

import lombok.Data;

/**
 * The weather forecast request object
 * 
 * @author arnidhi
 * @version 1.0.0
 */
@Data
public class WeatherForecastRequest {
	
	/**
	 * The city for which weather forecast is required
	 */
	private String city;
	
	/**
	 * The country of the city
	 */
	private String country;
	
	/**
	 * The time zone in which result is required default is UTC
	 */
	private String timeZone;
	
	
}
