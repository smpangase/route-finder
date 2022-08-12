package com.mpangase.rest.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;

@Entity
@Table(name = "planet")
public class Planet implements Serializable{
	

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	
	private String planetNode;
	
	private String planetName;
	
	@Transient
	private static final long serialVersionUID = 677333234182743087L;
	
	public Planet() {
		
	}
	
	public Planet(Integer id, String node, String name) {
		this.id = id;
		this.planetName = name;
		this.planetNode = node;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getPlanetNode() {
		return planetNode;
	}
	public void setPlanetNode(String node) {
		this.planetNode = node;
	}
	public String getPlanetName() {
		return planetName;
	}
	public void setPlanetName(String name) {
		this.planetName = name;
	}
	
	
}
