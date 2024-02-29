package com.mpangase.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import za.co.capitec.common.listeners.DefaultListenerSupport;
//import za.co.capitec.common.util.RetryTemplateBuilder;

import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.*;

@EnableRetry
@Configuration
public class RestConfig {

	@Value("${rest.config.resttemplate.timeoutMillisecs}")
	private long timeoutMilliSeconds;

	@Value("${rest.config.resttemplate.retryMaxAttempts}")
	private int retryMaxAttempts;
	@Value("${rest.config.resttemplate.retryBackoffMilliSecs}")
	private long retryBackoffMilliSecs;

	@Bean
	public RetryTemplate restRetryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();

		Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
		retryableExceptions.put(SocketTimeoutException.class, Boolean.TRUE);
		retryableExceptions.put(ResourceAccessException.class, Boolean.TRUE);

		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		fixedBackOffPolicy.setBackOffPeriod(retryBackoffMilliSecs);
		retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

		TimeoutRetryPolicy timeoutRetryPolicy = new TimeoutRetryPolicy();
		timeoutRetryPolicy.setTimeout(timeoutMilliSeconds);

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(retryMaxAttempts, retryableExceptions);
		retryPolicy.setMaxAttempts(retryMaxAttempts);
		CompositeRetryPolicy compositeRetryPolicy = new CompositeRetryPolicy();
		compositeRetryPolicy.setPolicies(new RetryPolicy[] {retryPolicy, timeoutRetryPolicy });

		retryTemplate.setRetryPolicy(compositeRetryPolicy);
		retryTemplate.registerListener(new DefaultListenerSupport());

		return retryTemplate;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		return builder.setConnectTimeout(Duration.ofMillis(timeoutMilliSeconds)).setReadTimeout(Duration.ofMillis(timeoutMilliSeconds)).build();
	}
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(
				new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		return restTemplate;
	}