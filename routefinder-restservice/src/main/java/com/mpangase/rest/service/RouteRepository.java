package com.mpangase.rest.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpangase.rest.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
}
