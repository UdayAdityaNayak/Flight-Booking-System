package com.flightbookingsystem.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightbookingsystem.entity.Flight;
import com.flightbookingsystem.repository.FlightRepository;
@Repository
public class FlightDao {

	@Autowired
	private FlightRepository flightRepository;
	
	public Flight addFlight(Flight flight) {
		return flightRepository.save(flight);
	}
	
	public List<Flight> getAllFlight(){
		return flightRepository.findAll();
	}
	public Optional<Flight> getFlighBytId(Integer id){
		return flightRepository.findById(id);
	}
	
	public List<Flight> getFlighBytSourceAndDestination(String source,String destination){
		return flightRepository.getFlightBySourceAndDestination(source, destination);
	}
	
	public List<Flight> getFlightByAirline(String airline){
		return flightRepository.getFlightByAirline(airline);
	}
	
	public void deleteFlight(Flight flight) {
		 flightRepository.delete(flight);
	}
	public Page<Flight> getFlightByPaginationAndSorting(Integer pageNumber,Integer pageSize,String field){
		return flightRepository.findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).ascending()));
	}
}
