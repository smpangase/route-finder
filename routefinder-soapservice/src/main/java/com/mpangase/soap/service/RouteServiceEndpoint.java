package com.mpangase.soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mpangase.soap.routeservice.*;
import com.mpangase.soap.utils.Constants;

@Endpoint
public class RouteServiceEndpoint {

	private RouteServiceRepository routeRepository;

	@Autowired
	public RouteServiceEndpoint(RouteServiceRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	@PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "RouteRequest")
	@ResponsePayload
	public RouteResponse calculateRoute(@RequestPayload RouteRequest request) {
		RouteResponse response = new RouteResponse();
		
		response.setResult(routeRepository.calculateRoute(request.getSource(), request.getDestination()));

		return response;
	}
}