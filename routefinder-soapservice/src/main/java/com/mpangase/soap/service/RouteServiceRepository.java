package com.mpangase.soap.service;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.mpangase.bfs.PathFinder;
import com.mpangase.soap.routeservice.*;
import com.mpangase.soap.routeservice.Result.Path;

@Component
public class RouteServiceRepository {
	private static final Logger log = LoggerFactory.getLogger(RouteServiceRepository.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value( "${routeFinder.restservice.baseurl}" )
	String routeFinderEndpointBaseUrl;
	
	public Result calculateRoute(String source, String destination) {
		Assert.notNull(source, "The Source planet name cannot be null");
		Assert.notNull(destination, "The Source planet name cannot be null");
		
		if(!PathFinder.graphInitialised) {
			retrieveGraphData();
		}
		Path path = PathFinder.getShortestRoute(destination);
		
		Result result = new Result();
		result.setCode(200);
		result.setMessage("successfully found shortest path");
		result.setPath(path);
		

		return result;
	}
	
	//@PostConstruct
	public void retrieveGraphData() {
		log.info("postconstruct " + routeFinderEndpointBaseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		 
		try {
			log.info("Getting planets");
			String planets = restTemplate.exchange(routeFinderEndpointBaseUrl+"/api/v1/planet", HttpMethod.GET,
					entity, String.class).getBody();

			JSONParser jsonParser = new JSONParser();
			JSONArray planetsObject = (JSONArray) jsonParser.parse(planets);
			log.info(planetsObject.toJSONString());
			log.info("Getting routes");
			String routes = restTemplate.exchange(routeFinderEndpointBaseUrl+"/api/v1/route", HttpMethod.GET,
					entity, String.class).getBody();

			JSONArray routesObject = (JSONArray) jsonParser.parse(routes);
			log.info(routesObject.toJSONString());
			
			PathFinder.initGraph(planetsObject, routesObject);
		} catch(Exception e) {
			
		}
		
		
	}

	
}