package com.sapient.weather.forecast.exception;

import java.util.Map;

import com.sapient.weather.forecast.handler.dto.ErrorCode;

/**
 * Exception thrown if client request is invalid
 *
 * @author arnidhi
 * @version 1.0.0
 */
public class BadRequestException extends ApiException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Create an instance of new Bad Request Exception with specified message
     *
     * @param message the specified message
     */
    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }

    /**
     * Create an instance of new Bad Request Exception with specified message and code
     *
     * @param code    the error code
     * @param message the specified message
     */
    public BadRequestException(String code, String message) {
        this(code, message, null);
    }

    /**
     * Create an instance of new Bad Request Exception with specified code, message and cause
     *
     * @param code    the specified code
     * @param message the specified message
     * @param cause   the specified cause
     */
    public BadRequestException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * Create an instance of new Bad Request Exception with specified code, message and cause
     *
     * @param code    the specified code
     * @param message the specified message
     * @param cause   the specified cause
     * @param info    the specified info
     */
    public BadRequestException(String code, String message, Throwable cause, Map<String, Object> info) {
        super(code, message, cause, info);
    }
}
