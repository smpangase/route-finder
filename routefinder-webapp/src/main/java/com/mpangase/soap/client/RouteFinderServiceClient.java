package com.mpangase.soap.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.mpangase.soap.client.gen.RouteRequest;
import com.mpangase.soap.client.gen.RouteResponse;


public class RouteFinderServiceClient extends WebServiceGatewaySupport{

	public RouteResponse findRoute(String url, String sourcePlanet, String destinationPlanet) {
		
		RouteRequest request = new RouteRequest();
		request.setSource(sourcePlanet);
		request.setDestination(destinationPlanet);
		RouteResponse response = (RouteResponse)getWebServiceTemplate().marshalSendAndReceive(url, request);
		
		return response;
	}
}
