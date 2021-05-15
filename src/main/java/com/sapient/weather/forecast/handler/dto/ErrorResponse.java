package com.sapient.weather.forecast.handler.dto;

import java.util.Map;

import lombok.*;

/**
 * The error response body
 * 
 * @author arnidhi
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {

	 /**
     * The application name
     */
    private String appName;

    /**
     * The application version
     */
    private String appVersion;

    /**
     * The error code
     */
    private String errorCode;

    /**
     * The error message based on locale
     */
    private String errorMessage;

    /**
     * The detail error message
     */
    private String detailErrorMessage;

}
