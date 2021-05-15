package com.sapient.weather.forecast.exception;

import java.util.Map;

import com.sapient.weather.forecast.handler.dto.ErrorCode;

import lombok.Getter;

/**
 * The root exception class for all the api's
 *
 * @author arnidhi
 * @version 1.0.0
 */
@Getter
public class ApiException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The specified error code for this exception.
     * Error code is mandatory
     */
    private final String code;

    /**
     * Any additional information specific to this exception
     */
    private final Map<String, Object> info;

    /**
     * Constructs a new instance of this exception with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.0.0
     */
    public ApiException(String message, Throwable cause) {
        this(ErrorCode.SERVER_ERROR, message, cause);
    }

    /**
     * Constructs a new instance of this exception with the specified code and detail message.
     *
     * @param code    the specified error code
     * @param message the detail message
     * @since 1.0.0
     */
    public ApiException(String code, String message) {
        this(code, message, null);
    }

    /**
     * Constructs a new instance of this exception with the specified code, detail message and
     * cause.
     *
     * @param code    the specified error code
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.0.0
     */
    public ApiException(String code, String message, Throwable cause) {
        this(code, message, cause, null);
    }

    /**
     * Constructs a new instance of this exception with the specified code, detail message,
     * cause and additional info
     *
     * @param code    the specified error code
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @param info    the specified additional info
     * @since 1.0.0
     */
    public ApiException(String code, String message, Throwable cause, Map<String, Object> info) {
        super(message, cause);
        this.code = code;
        this.info = info;
    }

}
