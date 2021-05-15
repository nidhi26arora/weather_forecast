package com.sapient.weather.forecast.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.sapient.weather.forecast.exception.BadRequestException;
import com.sapient.weather.forecast.exception.ServiceException;
import com.sapient.weather.forecast.handler.dto.ErrorCode;
import com.sapient.weather.forecast.handler.dto.ErrorResponse;
import com.sapient.weather.forecast.properties.WeatherForecast;

/**
 * Global controller level exception handler
 *
 * @author arnidhi
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    /**
     * Private static class level logger
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    /**
     * Resolve properties from spring *.yml files
     */
    @Autowired
    private WeatherForecast weatherForecast;

    /**
     * Handle any other exceptional cases
     *
     * @param th the cause
     * @return the error response
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Exception th) {
        logger.error("unknown server exception", th);
        return prepairErrorResponse(th, ErrorCode.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handle {@link com.fasterxml.jackson.core.JsonParseException}
     *
     * @param bre the cause
     * @return the error response
     */
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(JsonParseException bre) {
       return prepairErrorResponse(bre, ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handle any other exceptional cases
     *
     * @param th the cause
     * @return the error response
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException iae) {
        logger.error("unknown server exception", iae);
        return prepairErrorResponse(iae, ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle {@link HttpMessageNotReadableException}
     *
     * @param ex the exception to handle
     * @return the error response
     */
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.debug("handling message not readable exception", ex);
        return prepairErrorResponse(ex, ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle {@link BadRequestException}
     *
     * @param bre the cause
     * @return the error response
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        logger.debug("handling bad request error", bre);
        return prepairErrorResponse(bre, bre.getCode(), HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handle {@link HttpMessageNotReadableException}
     *
     * @param bre the cause
     * @return the error response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonMappingException(HttpMessageNotReadableException bre) {
        logger.debug("handling bad request error", bre);
        return prepairErrorResponse(bre, ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    
    /**
     * Handle {@link ServiceException}
     *
     * @param se the cause
     * @return the error response
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException se) {
        if (se.getCause() instanceof BadRequestException) {
            return handleBadRequestException((BadRequestException) se.getCause());
        } 

        logger.error("unknown server service exception", se);
        return prepairErrorResponse(se, se.getCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle {@link HttpMediaTypeNotSupportedException}
     *
     * @param hmtnse the cause
     * @return the error response
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException hmtnse) {
        logger.warn("Media Type is not supported", hmtnse);
        
        return prepairErrorResponse(hmtnse, ErrorCode.BAD_REQUEST, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Handle {@link MissingServletRequestParameterException}
     *
     * @param msr the cause
     * @return the error response
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException msr) {
        logger.warn("Required request parameter is not present", msr);
        return prepairErrorResponse(msr, ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }
    
    private ResponseEntity<ErrorResponse> prepairErrorResponse(Exception e, String errorCode, HttpStatus httpStatus){
    	 String message = StringUtils.isEmpty(e.getMessage()) ? weatherForecast.getGenericErrorMessage() : e.getMessage();
         ErrorResponse response = new ErrorResponse();
         response.setErrorCode(errorCode);
         response.setErrorMessage(message);
         response.setAppName(weatherForecast.getAppName());
         response.setAppVersion(weatherForecast.getAppVersion());

         return new ResponseEntity<ErrorResponse>(response, httpStatus);
    }

}
