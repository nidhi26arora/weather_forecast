package com.sapient.weather.forecast;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sapient.weather.forecast.properties.WeatherForecast;

/**
 * Actuator endpoints security configuration
 *
 * @author arnidhi
 * @version 1.0.0
 */
@Configuration
@Order(1)
public class ActuatorSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Noop password encoder id
     */
    private static final String NOOP_ENCODER_ID = "{noop}";

    /**
     * Application properties resolved from spring
     */
    @Autowired
    private WeatherForecast weatherForecast;

    /**
     * Configure in memory user details service for actuator user
     *
     * @param auth the authentication builder
     * @throws Exception if any error occurs
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(weatherForecast.getActuatorEndpointsUser())
                .password(String.format("%s%s", NOOP_ENCODER_ID, weatherForecast.getActuatorEndpointsUserPassword()))
                .authorities("ROLE_ACTUATOR");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf is disable, session is never created
        // form login is disabled
        http.requestMatchers()
                .antMatchers("/actuator/**")
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**")
                .fullyAuthenticated()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
                .rememberMe()
                .disable()
                .logout()
                .disable()
                .x509()
                .disable()
                .httpBasic()
                .and()
                .cors()
                .configurationSource(corsConfigurationSource());
    }

    /**
     * Only allow cors for /actuator/** requests
     *
     * @return {@link WebMvcConfigurer} instance
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        corsConfigurationSource.registerCorsConfiguration("/actuator/**", corsConfiguration);

        return corsConfigurationSource;
    }
}
