package com.sapient.weather.forecast.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.weather.forecast.exception.ApiException;
import com.sapient.weather.forecast.exception.ServiceException;
import com.sapient.weather.forecast.handler.dto.ErrorCode;

public class ExcernalClientUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcernalClientUtil.class);
	
	@SuppressWarnings("unchecked")
	public static <T> T doCallExternalApi(HttpUriRequest httpUriRequest, Class<T> clazz, CloseableHttpClient httpClient, ObjectMapper objectMapper) throws ApiException {
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest)) {
            String json = EntityUtils.toString(httpResponse.getEntity());
            logger.debug("json response [{}] and status code {} after calling http uri request [{}]", json, httpResponse.getStatusLine().getStatusCode(),httpUriRequest.getURI());
            EntityUtils.consume(httpResponse.getEntity());
            if (httpResponse.getStatusLine().getStatusCode() < 499 && httpResponse.getStatusLine().getStatusCode() >= 200) {
                // parse the response only if response can be parsed
                if (StringUtils.isNotBlank(json)) {
                	if(clazz == String.class){
                		return (T) json;
                	}
                    T apiResponse = objectMapper.readValue(json.trim(), clazz);

                    return apiResponse;
                }
                return null;
            }

            logger.info("Error response {} for http uri request {}",json, httpUriRequest.getURI());
            throw new ServiceException(ErrorCode.EXTERNAL_SERVICE_INVALID_ERROR_RESPONSE, json);
        } catch (IOException ex) {
            throw new ApiException(ErrorCode.EXTERNAL_SERVICE_ERROR, "failed to call external service", ex);
        }
    }

}
