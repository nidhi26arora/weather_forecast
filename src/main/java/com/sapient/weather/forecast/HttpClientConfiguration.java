package com.sapient.weather.forecast;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;

import com.google.common.collect.Lists;
import com.sapient.weather.forecast.properties.WeatherForecast;

/**
 * Http client configuration
 *
 * @author arnidhi
 * @version 1.0.0
 */
@Configuration
public class HttpClientConfiguration {

    /**
     * the static final logger 
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpClientConfiguration.class);

    /**
     * Get the http client bean
     *
     * @return the http client bean
     */
    @Bean
    @Primary
    public CloseableHttpClient httpClient(WeatherForecast weatherForecast) {
    	PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
    	pool.setDefaultMaxPerRoute(weatherForecast.getMaxConnectionPerRoute());
    	pool.setMaxTotal(weatherForecast.getMaxConnections());
    	
        final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(weatherForecast.getConnectionTimeOut())
                .setConnectionRequestTimeout(weatherForecast.getRequestTimeOut())
                .setSocketTimeout(weatherForecast.getSocketTimeOut())
                .build();

        Header acceptHeader = new BasicHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        List<Header> defaultHeaders = Lists.newArrayList(acceptHeader);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(pool)
                .setMaxConnTotal(weatherForecast.getMaxConnections())
                .setMaxConnPerRoute(weatherForecast.getMaxConnectionPerRoute())
                .setDefaultRequestConfig(requestConfig)
                .setDefaultHeaders(defaultHeaders)
                .setRetryHandler((exception, executionCount, context) -> {
                    if (executionCount > 3) {
                    	logger.warn("Maximum tries reached for client http pool ",exception);
                        return false;
                    }
                    if (exception instanceof org.apache.http.NoHttpResponseException) {
                    	logger.warn("No response from server on " + executionCount + " call", exception);
                        return true;
                    }
                    return false;
                })
                .build();

        return httpClient;
    }

}
