package com.flightbookingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightbookingsystem.Dao.PassengerDao;
import com.flightbookingsystem.Dto.ResponseStructure;
import com.flightbookingsystem.entity.Flight;
import com.flightbookingsystem.entity.Passenger;
import com.flightbookingsystem.exception.IdNotFoundException;
import com.flightbookingsystem.exception.NoRecordFoundException;


@Service
public class PassengerService {
	@Autowired
	private PassengerDao passengerDao;

	public ResponseEntity<ResponseStructure<Passenger>> addPassenger(Passenger passenger) {
		ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Passenger record is saved");
		response.setData(passengerDao.addPassenger(passenger));
		return new ResponseEntity<ResponseStructure<Passenger>>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassenger() {
		ResponseStructure<List<Passenger>> response = new ResponseStructure<>();
		List<Passenger> pl = passengerDao.getAllPassenger();
		if (!pl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All Passenger record is fetched successfully");
			response.setData(pl);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new NoRecordFoundException("There is no record available in the database");
		}
	}

	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(Integer id) {
		ResponseStructure<Passenger> response = new ResponseStructure<>();
		Optional<Passenger> opt = passengerDao.getPassengerById(id);
		if (opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Passenger record is Fetched successfully with the given id");
			response.setData(opt.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("There is no passenger with the given id");
		}

	}

	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(Passenger passenger) {
		ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();
		if (passenger.getId() == null) {
			throw new IdNotFoundException("ID must be provided to update passenger details");
		}

		Optional<Passenger> opt = passengerDao.getPassengerById(passenger.getId());
		if (opt.isEmpty()) {
			throw new NoRecordFoundException("No passenger found with the given ID");
		}

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Passenger record is Fetched successfully with the given id");
		response.setData(passengerDao.addPassenger(passenger));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContactNumber(Long contactNumber){
		
		ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();
		
		Passenger p =passengerDao.getPassengerByContactNumber(contactNumber);
		if(p!=null) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Passenger record is fatched successfully with given contact number");
			response.setData(p);
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}else {
			throw new NoRecordFoundException("There is no record available with the given contact number");
		}

	}
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerByPaginationAndSorting(Integer pageNumber,Integer pageSize,String field){
		ResponseStructure<Page<Passenger>> response = new ResponseStructure<>();
		
		Page<Passenger> pl=passengerDao.getPassengerByPaginationAndSorting(pageNumber, pageSize, field);
		if(!pl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Passenger record is fatched successfully using pagination and sortingr");
			response.setData(pl);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new NoRecordFoundException("There is no record available with the given pagination or sorting details");
		}
	}
	public ResponseEntity<ResponseStructure<String>> deletePassenger(Integer  id){
		ResponseStructure<String> response=new ResponseStructure<String>();
				passengerDao.deletePassenger(id);
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("Passenger record is deleted successfully");
				response.setData("sucess");
				return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
			

		}

}
