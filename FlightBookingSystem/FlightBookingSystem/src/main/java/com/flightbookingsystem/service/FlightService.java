package com.flightbookingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightbookingsystem.Dao.FlightDao;
import com.flightbookingsystem.Dto.ResponseStructure;
import com.flightbookingsystem.entity.Flight;
import com.flightbookingsystem.exception.IdNotFoundException;
import com.flightbookingsystem.exception.NoRecordFoundException;

@Service
public class FlightService {
	@Autowired
	private FlightDao flightDao;
	
	public ResponseEntity<ResponseStructure<Flight>> addFlight(Flight flight){
		ResponseStructure<Flight> response=new ResponseStructure<Flight>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight record is saved");
		response.setData(flightDao.addFlight(flight));
		return new ResponseEntity<ResponseStructure<Flight>>(response,HttpStatus.CREATED);
	}
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlight(){
		ResponseStructure<List<Flight>> response=new ResponseStructure<>();
		List<Flight> fl=flightDao.getAllFlight();
		if(!fl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All the Flight record is fatched successfully");
			response.setData(fl);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			throw new NoRecordFoundException("No record found in the databse");
		}
		
	}
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(Integer id){
		ResponseStructure<Flight> response=new ResponseStructure<Flight>();
		Optional<Flight> opt=flightDao.getFlighBytId(id);
		if(!opt.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("The Flight record with the given id is fetched successfully");
			response.setData(opt.get());
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			throw new IdNotFoundException("No record found in the databse with this id");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlighBytSourceAndDestination(String source,String destination){
		ResponseStructure<List<Flight>> response=new ResponseStructure<>();
		List<Flight> fl=flightDao.getFlighBytSourceAndDestination(source,destination);
		if(!fl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight record is feted successfully");
			response.setData(fl);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			throw new NoRecordFoundException("No record is found as source or destination are invalid");
		}
		
	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>>  getFlightByAirline(String airline){
		ResponseStructure<List<Flight>> response=new ResponseStructure<>();
		List<Flight> fl=flightDao.getFlightByAirline(airline);
		if(!fl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight record with given airline is feted successfully");
			response.setData(fl);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			throw new NoRecordFoundException("No record is found as airline info  is not available in the database");
		}
		
	}
	
	public ResponseEntity<ResponseStructure<Flight>> updateFlight( Flight flight) {
	    ResponseStructure<Flight> response = new ResponseStructure<>();

	    if (flight.getId() == null) {
	        throw new IdNotFoundException("Flight ID is required for update");
	    }

	    else if (flightDao.getFlighBytId(flight.getId()).isEmpty()) {
	        throw new IdNotFoundException("No record found in the database with this ID");
	    }

	    else {
		    Flight updated = flightDao.addFlight(flight);

		    response.setStatusCode(HttpStatus.OK.value());
		    response.setMessage("Flight record updated successfully");
		    response.setData(updated);

		    return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	}
	
	public ResponseEntity<ResponseStructure<Flight>> deleteFlight(Integer  id){
		ResponseStructure<Flight> response=new ResponseStructure<Flight>();
		Optional<Flight> opt=flightDao.getFlighBytId(id);
			if(!opt.isEmpty()) {
				flightDao.deleteFlight(opt.get());
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("Flight record is deleted successfully");
				response.setData(null);
				return new ResponseEntity<ResponseStructure<Flight>>(response,HttpStatus.CREATED);
			}
			else {
				throw new IdNotFoundException("No record found in the databse with this id");
			}

		}
	
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightByPaginationAndSorting(Integer pageNumber,Integer pageSize,String field){
		ResponseStructure<Page<Flight>> response=new ResponseStructure<>();
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Flight record is fatched successfully");
		response.setData(flightDao.getFlightByPaginationAndSorting(pageNumber,pageSize,field));
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}

		
	
	
	
}
