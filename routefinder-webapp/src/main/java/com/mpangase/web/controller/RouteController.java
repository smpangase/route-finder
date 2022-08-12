package com.mpangase.web.controller;


import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpangase.soap.client.RouteFinderServiceClient;
import com.mpangase.soap.client.gen.RouteResponse;

@RestController
@RequestMapping("/api")
public class RouteController {
	private static final Logger log = LoggerFactory.getLogger(RouteController.class);
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RouteFinderServiceClient client;
	
	@Value( "${routeFinder.soapservice.baseurl}" )
	String routeFinderSoapEndpointBaseUrl;
	
	@Value( "${routeFinder.restservice.baseurl}" )
	String routeFinderRestEndpointBaseUrl;
	
	@GetMapping("/v1/getroute/{id}/traffic/{traffic}")
	public ResponseEntity<?>  getShortestRoute(@PathVariable(value = "id") int id, @PathVariable(value = "traffic") boolean traffic) {
		//get routing
		AjaxResponseBody result = new AjaxResponseBody();
		String source = "Earth";
		
		String destination = getPlanetsName(id);
		if(destination != null) {
			log.info("Getting route to " + destination);
			RouteResponse response = client.findRoute(routeFinderSoapEndpointBaseUrl +"/service/routeService", source, destination);
			
			if(response.getResult().getCode() != 200) {
		         result.setResultCode("failed");
		         result.setResultMsg("Error while getting shortest route From "+ source + " to " + destination);
		         return ResponseEntity.badRequest().body(response.getResult().getMessage());
			}
			
			List<?> planets = response.getResult().getPath().getPlanet();
	        
			result.setResultCode("success");
	        result.setResultMsg("successfully mapped the shortest route From "+ source + " to " + destination);
	       
	        ObjectMapper obj = new ObjectMapper();  
	        String payload;
			try {
				payload = obj.writeValueAsString(planets);
				result.setPayload(payload);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			result.setResultCode("failed");
	        result.setResultMsg("Error: Unknown planet");
	        return ResponseEntity.badRequest().body("Error: Unknown planet");
		}
        

        return ResponseEntity.ok(result);
	}
	
	public String getPlanetsName(int planetId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String planetName = null;
		try {
			String planets = restTemplate.exchange(routeFinderRestEndpointBaseUrl+"/api/v1/planet", HttpMethod.GET,
					entity, String.class).getBody();

			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArrayPlanets = (JSONArray) jsonParser.parse(planets);
			for(int i = 0; i < jsonArrayPlanets.size(); i++) {
				JSONObject obj = (JSONObject) jsonArrayPlanets.get(i);
				if(planetId == Integer.parseInt(obj.get("id").toString())) {
					planetName = obj.get("planetName").toString();
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return planetName;
	}
	
	class AjaxResponseBody {

	    String resultCode;
	    String resultMsg;
	    String payload;
	    
	    public void setResultCode(String code) {
	    	this.resultCode = code;
	    }
	    
	    public String getResultCode() {
	    	return this.resultCode;
	    }
	    
	    public void setResultMsg(String message) {
	    	this.resultMsg = message;
	    }
	    
	    public String getResultMsg() {
	    	return this.resultMsg;
	    }
	    
	    public void setPayload(String payload) {
	    	this.payload = payload;
	    }
	   
	    public String getPayload() {
	    	return this.payload;
	    }
	}

}
