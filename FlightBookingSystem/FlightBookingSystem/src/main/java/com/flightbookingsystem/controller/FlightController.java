package com.flightbookingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbookingsystem.Dto.ResponseStructure;
import com.flightbookingsystem.entity.Flight;
import com.flightbookingsystem.service.FlightService;
@RequestMapping("/flight")
@RestController
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Flight>> addFlight(@RequestBody Flight flight){
		return flightService.addFlight(flight);
	}
	@GetMapping("/All")
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlight(){
		return flightService.getAllFlight();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(@PathVariable Integer id){
		return flightService.getFlightById(id);
	}
	
	@GetMapping("/{source}/{destination}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlighBytSourceAndDestination(@PathVariable String source,@PathVariable String destination){
		return flightService.getFlighBytSourceAndDestination(source, destination);
	}
	
	@GetMapping("/Airline/{airline}")
	public ResponseEntity<ResponseStructure<List<Flight>>>  getFlightByAirline(@PathVariable String airline){
		return flightService.getFlightByAirline(airline);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(@RequestBody Flight flight){
		return flightService.updateFlight(flight);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> deleteFlight(@PathVariable Integer id){
		return flightService.deleteFlight(id);
	}
	
	@GetMapping("/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightByPaginationAndSorting(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,@PathVariable String field){
		return flightService.getFlightByPaginationAndSorting(pageNumber, pageSize, field);
	}
}
