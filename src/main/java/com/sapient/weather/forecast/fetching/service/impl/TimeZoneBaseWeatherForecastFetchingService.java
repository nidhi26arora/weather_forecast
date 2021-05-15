package com.sapient.weather.forecast.fetching.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchRequest;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchResponse;
import com.sapient.weather.forecast.fetching.service.WeatherForecastFetchingService;

@Service("timeZoneBaseWeatherForecastFetchingService")
@Primary
public class TimeZoneBaseWeatherForecastFetchingService implements WeatherForecastFetchingService {

	private static final String UTC_TIME_ZONE = "Etc/UTC";
	
	@Autowired
	@Qualifier("cachableWeatherForecastFetchingService")
	private WeatherForecastFetchingService weatherForecastFetchingService;
	
	@Override
	public List<WeatherForecastFetchResponse> fetchForecast(WeatherForecastFetchRequest weatherForecastFetchRequest) throws ApiException {
		
		String zoneId = weatherForecastFetchRequest.getWeatherForecastRequest().getTimeZone();
		if(StringUtils.isBlank(zoneId)){
			zoneId = UTC_TIME_ZONE;
		}
		weatherForecastFetchRequest.setMinFetchTimeInSecs(startOfTheDay(zoneId));
		return weatherForecastFetchingService.fetchForecast(weatherForecastFetchRequest);
	}
	
	private Long startOfTheDay(String strZoneId){
		ZoneId zoneId = ZoneId.of(strZoneId);
		LocalDate today = LocalDate.now(zoneId);
		today = today.plusDays(1);
		ZonedDateTime startOfTheDay = today.atStartOfDay(zoneId);
		return  startOfTheDay.toEpochSecond();
	}

}
