package com.sapient.weather.forecast.fetching.service.impl;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.exception.BadRequestException;
import com.sapient.weather.forecast.exception.ServiceException;
import com.sapient.weather.forecast.fetching.dto.OpenWeatherDateWiseList;
import com.sapient.weather.forecast.fetching.dto.OpenWeatherResponseDTO;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchRequest;
import com.sapient.weather.forecast.fetching.dto.WeatherForecastFetchResponse;
import com.sapient.weather.forecast.fetching.service.WeatherForeCastFetcher;
import com.sapient.weather.forecast.handler.dto.ErrorCode;
import com.sapient.weather.forecast.properties.WeatherForecast;
import com.sapient.weather.forecast.util.ExcernalClientUtil;

@Service("openWeatherForeCastFetcher")
public class OpenWeatherForeCastFetcher implements WeatherForeCastFetcher {
	
	@Autowired 
	private WeatherForecast weatherForecast;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CloseableHttpClient httpClient;
	
	private static final String OPEN_WEATHER_FORECAST_FEATCHER_URL= "http://api.openweathermap.org/data/2.5/forecast";
	
	private static final String API_ID_KEY = "appid";
	
	private static final String CITY_PARAM = "q";
	
	private static final List<String> rainyKeywords = Arrays.asList(new String []{"Drizzle","Rain","Thunderstorm"});
	

	@Override
	public List<WeatherForecastFetchResponse> fetchWeatherForecasting(
			WeatherForecastFetchRequest weatherForecastFetchRequest) throws ApiException {
		try{
			final URIBuilder applicationUriBuilder = new URIBuilder(OPEN_WEATHER_FORECAST_FEATCHER_URL);
			applicationUriBuilder.addParameter(API_ID_KEY, weatherForecast.getOpenWeatherApiId());
	        applicationUriBuilder.addParameter(CITY_PARAM, getCityParamValue(weatherForecastFetchRequest));
	        applicationUriBuilder.addParameter("units", "metric");
	 
	        HttpGet httpGet = new HttpGet(applicationUriBuilder.build());
	        OpenWeatherResponseDTO openWeatherResponseDTO = ExcernalClientUtil.doCallExternalApi(httpGet, OpenWeatherResponseDTO.class, httpClient, objectMapper);
	        return adapTo(openWeatherResponseDTO);
		}catch(URISyntaxException ex){
			throw new ApiException(ErrorCode.EXTERNAL_SERVICE_ERROR, "Un-anle to fetch weather forecast",ex);
		}
  	}
	
	private List<WeatherForecastFetchResponse> adapTo(OpenWeatherResponseDTO openWeatherResponseDTO) throws ApiException{
		if(openWeatherResponseDTO==null){
			throw new ServiceException(ErrorCode.EXTERNAL_SERVICE_ERROR, "Un-able to fetch details from external service");
		}
		if(StringUtils.isBlank(openWeatherResponseDTO.getCod())){
			throw new ServiceException(ErrorCode.EXTERNAL_SERVICE_INVALID_ERROR_RESPONSE, "Un-able to fetch details from external service");
		}
		if(openWeatherResponseDTO.getCod().equals(200)){
			throw new BadRequestException(openWeatherResponseDTO.getCod(),openWeatherResponseDTO.getMessage());
		}
		List<WeatherForecastFetchResponse> response = new ArrayList<WeatherForecastFetchResponse>();
		if(CollectionUtils.isEmpty(openWeatherResponseDTO.getList())){
			return response;
		}
		for(OpenWeatherDateWiseList openWeatherDateWiseList: openWeatherResponseDTO.getList()){
			WeatherForecastFetchResponse weatherForecastFetchResponse = new WeatherForecastFetchResponse();
			weatherForecastFetchResponse.setDate(openWeatherDateWiseList.getDt());
			weatherForecastFetchResponse.setTemprature(openWeatherDateWiseList.getMain()!=null ? openWeatherDateWiseList.getMain().getTemp():null);
			boolean isRainy = false;
			if(!CollectionUtils.isEmpty(openWeatherDateWiseList.getWeather())){
				isRainy = openWeatherDateWiseList.getWeather().stream().anyMatch(main -> rainyKeywords.contains(main.getMain()));
			}
			weatherForecastFetchResponse.setRainyDay(isRainy);
			response.add(weatherForecastFetchResponse);
		}
		return response;
	
	}
	
	private String getCityParamValue(WeatherForecastFetchRequest weatherForecastFetchRequest){
		StringBuilder stringBuilder = new StringBuilder(weatherForecastFetchRequest.getWeatherForecastRequest().getCity());
		if(StringUtils.isNotBlank(weatherForecastFetchRequest.getWeatherForecastRequest().getCountry())){
			stringBuilder.append(",").append(weatherForecastFetchRequest.getWeatherForecastRequest().getCountry());
		}
		return stringBuilder.toString();
	}
	
	

	@Override
	public boolean isEnabled(WeatherForecastFetchRequest weatherForecastFetchRequest) {
		return weatherForecast.isFetchForeCastFromOpenWeather();
	}

}
