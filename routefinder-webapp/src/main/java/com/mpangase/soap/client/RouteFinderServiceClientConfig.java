package com.mpangase.soap.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class RouteFinderServiceClientConfig {

	@Bean
    public Jaxb2Marshaller marshaller() {
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
            marshaller.setContextPath("com.mpangase.soap.client.gen");
            return marshaller;
    }

    @Bean
    public RouteFinderServiceClient routeFinderClient(Jaxb2Marshaller marshaller) {
    	RouteFinderServiceClient client = new RouteFinderServiceClient();
            client.setDefaultUri("http://localhost:8080/routefinder-soapservice/service/routeService");
            client.setMarshaller(marshaller);
            client.setUnmarshaller(marshaller);
            return client;
    }
}
