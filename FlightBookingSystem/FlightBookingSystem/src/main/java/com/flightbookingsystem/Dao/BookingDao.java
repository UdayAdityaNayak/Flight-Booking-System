package com.flightbookingsystem.Dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightbookingsystem.entity.Booking;
import com.flightbookingsystem.repository.BookingRepository;
@Repository
public class BookingDao {
	@Autowired
	private BookingRepository bookingRepository;
	
	public Booking addBooking(Booking booking) {
		return bookingRepository.save(booking);
	}
	public List<Booking> getAllBooking() {
		return bookingRepository.findAll();
	}
	public Optional<Booking> getBookingById(Integer id){
		return bookingRepository.findById(id);
	}
	public List<Booking> getBookingByFlightId(Integer flightId) {
	    return bookingRepository.findByFlightId(flightId);
	}
	
	public List<Booking> getBookingByDate(LocalDateTime bookingDate){
		return bookingRepository.findByBookingDate(bookingDate);
	}
	public List<Booking> getBookingByStatus(Booking.Status status){
		return bookingRepository.findByStatus(status);
	}
	public Optional<Booking> getPaymentByBookingId(Integer id)
	{
		return bookingRepository.findById(id);
	}
	public Optional<Booking> getPassengerById(Integer id)
	{
		return bookingRepository.findById(id);
	}
    public Booking updateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    public void deleteBooking(Booking booking) {
         bookingRepository.delete(booking);
    }
    public Page<Booking> GetBookingByPaginationAndSorting(Integer pageNumber,Integer pageSize,String field){
    	return bookingRepository.findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).ascending()));
    }
    
}
