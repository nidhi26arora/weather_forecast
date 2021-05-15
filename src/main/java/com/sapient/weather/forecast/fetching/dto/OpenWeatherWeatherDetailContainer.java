package com.sapient.weather.forecast.fetching.dto;

import lombok.Data;

@Data
public class OpenWeatherWeatherDetailContainer {

	private Long id;
    private String main;
    private String description;
    private String icon;
}
