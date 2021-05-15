package com.sapient.weather.forecast.properties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class to hold all the application specific properties
 * 
 * @author arnidhi
 *
 */
@Component
@ConfigurationProperties("weather.forecast")
@Validated
@Getter
@Setter
public class WeatherForecast {
	
	/**
     * The app name
     */
    @NotEmpty
    private String appName;

    /**
     * The app version
     */
    @NotEmpty
    private String appVersion;

    /**
     * Generic error message for the server
     */
    @NotEmpty
    private String genericErrorMessage;
    
    /**
     *The connection time out
     */
    @NotNull
    private Integer connectionTimeOut;

    /**
     * The http connection request time out
     */
    @NotNull
    private Integer requestTimeOut;

    /**
     * The http connection socket time out
     */
    @NotNull
    private Integer socketTimeOut;

    /**
     * The http max connections
     */
    @NotNull
    private Integer maxConnections;

    /**
     * The max http connection per route
     */
    @NotNull
    private Integer maxConnectionPerRoute;
    
    /**
     * The user for actuator endpoints
     */
    @NotEmpty
    private String actuatorEndpointsUser;

    /**
     * The password for actuator endpoints user
     */
    @NotEmpty
    private String actuatorEndpointsUserPassword;
    
    @NotNull
    private boolean fetchForeCastFromOpenWeather;
    
    
    private String openWeatherApiId;
    
    
    
}
