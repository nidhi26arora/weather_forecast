package com.sapient.weather.forecast.handler.dto;

public class ErrorCode {

    /**
     * The error code to represent bad request from the client must translate to 400 http status code
     */
    public static final String BAD_REQUEST = "BAD_REQUEST";

    /**
     * The error code is not known
     */
    public static final String UNKNOWN = "UNKNOWN";

    /**
     * Indicates that external service returned non ok status code
     * This error code means that client successfully called the external service but external service returned
     * non 2xx stats code
     */
    public static final String NON_OK_STATUS_CODE = "NON_OK_STATUS_CODE";

    /**
     * External service error
     * Error the occur while calling the external service or while parsing the external service urls
     */
    public static final String EXTERNAL_SERVICE_ERROR = "EXTERNAL_SERVICE_ERROR";

    /**
     * Return invalid error code for the external api
     * This means that we were able to call external service but we can't parse the error code returned by external service
     */
    public static final String EXTERNAL_SERVICE_INVALID_ERROR_CODE = "EXTERNAL_SERVICE_INVALID_ERROR_CODE";

    /**
     * This error code means that external service returned invalid error response
     * This is an interface contract violation
     */
    public static final String EXTERNAL_SERVICE_INVALID_ERROR_RESPONSE = "EXTERNAL_SERVICE_INVALID_ERROR_RESPONSE";

    /**
     * Error code to indicate the requested rest resource is not found
     */
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    
    /**
     * Represents the error when server is failed to process the request
     * This indicates that request is made successfully by the client and server has issues to process the request.
     * Should translate to 500 http status code
     */
    public static final String SERVER_ERROR = "SERVER_ERROR";

}
