package com.mpangase.soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RouteFinderSoapServiceApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(RouteFinderSoapServiceApplication.class, args);
        
        //pre-load path
    }
    
    @Bean
   	public RestTemplate restTemplate() {
   	    return new RestTemplate();
   	}
}
