package com.mpangase.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	@Autowired
	RestTemplate restTemplate;
	
	@Value( "${routeFinder.restservice.baseurl}" )
	String routeFinderEndpointBaseUrl;
	
	String planets;
	JSONArray jsonObject = new JSONArray();

	@ModelAttribute
	public void preLoad(Model model) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		try {
			planets = restTemplate.exchange(routeFinderEndpointBaseUrl+"/api/v1/planet", HttpMethod.GET,
					entity, String.class).getBody();

			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONArray) jsonParser.parse(planets);
			log.info(jsonObject.toJSONString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("options", getPlanetOptions());
		return "home.html";
	}
	
	private Map<Integer, String> getPlanetOptions () {
		Map<Integer, String> options = new HashMap<Integer, String>();
		for (int i = 0; i < jsonObject.size(); i++) {
			JSONObject object = (JSONObject) jsonObject.get(i);
			if(!"Earth".equals(object.get("planetName")))
				options.put(Integer.parseInt(object.get("id").toString()), object.get("planetName").toString());
		}
		
		return options;
	}

}
