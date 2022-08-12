package com.mpangase.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpangase.rest.model.Route;
import com.mpangase.rest.service.RouteRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name="Routes")
public class RouteController {
	
	private final RouteRepository repository;

    public RouteController(RouteRepository repository) {
        this.repository = repository;
    }
		
	@GetMapping("/v1/route/{id}")
	public Optional<Route> get(@PathVariable(value = "id") Integer id) {
		return repository.findById(id);
	}
	
	@GetMapping("/v1/route")
	public List<Route> getAll() {
		return repository.findAll();
	}
	
	@PostMapping("/v1/route")
	public Route add(Route object ) {
		return repository.save(object);
	}
	
	@DeleteMapping("/v1/route/{id}")
	public boolean delete(@PathVariable(value = "id") Integer id) throws Throwable {
		repository.findById(id).orElseThrow(null);
		repository.deleteById(id);
		return true;
	}
}
