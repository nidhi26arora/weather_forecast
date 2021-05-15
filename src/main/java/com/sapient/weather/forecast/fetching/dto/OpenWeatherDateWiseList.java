package com.sapient.weather.forecast.fetching.dto;

import java.util.List;

import lombok.Data;

@Data
public class OpenWeatherDateWiseList {
	
	private Long dt;
	
	private OpenWeatherTempContainer main;
	
	private List<OpenWeatherWeatherDetailContainer> weather;

}
