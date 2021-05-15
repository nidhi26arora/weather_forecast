package com.sapient.weather.forecast.fetching.dto;

import java.util.List;

import lombok.Data;

@Data
public class OpenWeatherResponseDTO {

		private String cod;
		
		private String message;
		
		private Integer cnt;
		
		private List<OpenWeatherDateWiseList> list;
}
