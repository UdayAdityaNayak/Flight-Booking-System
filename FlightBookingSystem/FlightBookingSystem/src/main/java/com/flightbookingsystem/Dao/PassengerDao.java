package com.flightbookingsystem.Dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightbookingsystem.entity.Passenger;
import com.flightbookingsystem.exception.IdNotFoundException;
import com.flightbookingsystem.repository.PassengerRepository;
@Repository
public class PassengerDao {

	@Autowired
	private PassengerRepository passengerRepository;
	
	public Passenger addPassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}
	public List<Passenger> getAllPassenger(){
		return passengerRepository.findAll();
	}
	public Optional<Passenger> getPassengerById(Integer id) {
		return passengerRepository.findById(id);
	}
	public Passenger updatePassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}
	public Passenger getPassengerByContactNumber(Long contactNumber) {
		return passengerRepository.getPassengerByContactNumber(contactNumber);
	}
	public Page<Passenger> getPassengerByPaginationAndSorting(Integer pageNumber,Integer pageSize,String field){
		return passengerRepository.findAll(PageRequest.of(pageNumber,pageSize,Sort.by(field).ascending()));
	}
	public void deletePassenger(Integer id) {
		Optional<Passenger> opt= passengerRepository.findById(id);
		if(opt.isPresent()) {
			passengerRepository.delete(opt.get());
		}
		else {
			throw new IdNotFoundException("Id not find");
		}
	}
	
}
