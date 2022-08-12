package com.mpangase.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpangase.rest.model.Planet;
import com.mpangase.rest.service.PlanetRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name="Planets")
public class PlanetController {
	private final PlanetRepository planetRepository;

    public PlanetController(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }
	
	@GetMapping("/v1/planet/{id}")
	public Optional<Planet> get(@PathVariable(value = "id") Integer id) {
		return planetRepository.findById(id);
	}
	
	@GetMapping("/v1/planet")
	public List<Planet> getAll() {
		return planetRepository.findAll();
	}
	
	@PostMapping("/v1/planet")
	public Planet add(Planet object ) {
		return planetRepository.save(object);
	}
	
	@DeleteMapping("/v1/planet/{id}")
	public boolean delete(@PathVariable(value = "id") Integer id) throws Throwable {
		planetRepository.findById(id).orElseThrow(null);
		planetRepository.deleteById(id);
		return true;
	}
}
