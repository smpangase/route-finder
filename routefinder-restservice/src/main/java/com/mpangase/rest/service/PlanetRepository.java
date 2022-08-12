package com.mpangase.rest.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpangase.rest.model.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Integer> {
}
