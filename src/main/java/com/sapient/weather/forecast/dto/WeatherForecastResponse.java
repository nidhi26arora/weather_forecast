package com.sapient.weather.forecast.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Weather forecast detail response container
 * 
 * @author arnidhi
 * @version 1.0.0
 */
@Data
@Builder
public class WeatherForecastResponse {
	
	/**
	 * The date for which weather is forecasted 
	 */
	private Long date;
	
	/**
	 * The minimum temperature of the day 
	 */
	private float minTemp;
	
	/**
	 * The maximum temperature of the day 
	 */
	private float maxTemp;
	
	/**
	 * The list of messages for the day 
	 */
	private List<String> weatherMessage;

}
