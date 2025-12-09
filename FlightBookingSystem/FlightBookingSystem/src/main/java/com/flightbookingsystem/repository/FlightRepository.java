package com.flightbookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbookingsystem.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight ,Integer>{

	public List<Flight> getFlightBySourceAndDestination(String destination, String destination2);
	public List<Flight> getFlightByAirline(String airline);

}
