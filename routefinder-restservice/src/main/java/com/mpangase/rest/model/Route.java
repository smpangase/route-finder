package com.mpangase.rest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@Entity
@Table(name="route")
public class Route implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	
	private String origin;
	
	private String destination;
	
	private Double distance;
	
	private Double delay;
	
	private static final long serialVersionUID = -3945814834534071092L;
	
	public Route() {
		
	}
	
	public Route(String origin, String destination, Double distance, Double delay) {
		this.origin = origin;
		this.destination = destination;
		this.distance = distance;
		this.delay = delay;
	}
	
	
	public Integer getId() {
		return id;
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Double getDelay() {
		return delay;
	}
	public void setDelay(Double delay) {
		this.delay = delay;
	}
}
