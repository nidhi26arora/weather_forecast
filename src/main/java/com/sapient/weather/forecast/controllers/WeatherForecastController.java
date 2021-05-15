package com.sapient.weather.forecast.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.weather.forecast.dto.ListWeatherForecastResponse;
import com.sapient.weather.forecast.dto.WeatherForecastRequest;
import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.exception.ServiceException;
import com.sapient.weather.forecast.service.WeatherForecastingService;


/**
 * Rest Controller for weather forecast
 * 
 * @author arnidhi
 * @version 1.0.0
 */
@RestController
@RequestMapping("/")
public class WeatherForecastController {

	
	/**
	 * The private static logger 
	 */
	private static final Logger logger = LoggerFactory.getLogger(WeatherForecastController.class);

	/**
	 * Spring bean for {@link WeatherForecastingService}
	 */
	@Autowired
	private WeatherForecastingService weatherForecastingService;
	
	

	
	/**
	 * Upload file in minio server
	 * @param bucketName Bucket name.
	 * @param filePath File path including file name to be created inside bucket.
	 * @param file Data store in bucket at file path.
	 * @param contentType The file content type
	 * @param authKey The internal auth key
	 * @return The file processor response
	 * @throws ApiException 
	 * @throws ServiceException 
	 * @throws NotFoundException 
	 */
	@GetMapping(value = "/public/v1/fetch-forecast")
	public ResponseEntity<ListWeatherForecastResponse> fetchWeatherForeCast(WeatherForecastRequest weatherForecastRequest)throws  ApiException {

		assertNotNull(weatherForecastRequest,"Required parameters are missing");
		assertNotNull(weatherForecastRequest.getCity(), "Required city parameter is missing");
		ListWeatherForecastResponse response = weatherForecastingService.forecastWeather(weatherForecastRequest);
		return ResponseEntity.ok(response);
	}
	
	/**
     * Assert that variable is not null
     *
     * @param arg     the argument to validate
     * @param message the message if argument is invalid
     */
    private void assertNotNull(Object arg, String message) throws IllegalArgumentException {
        if (arg == null) {
            throw new IllegalArgumentException(message);
        }
    }
	
}
