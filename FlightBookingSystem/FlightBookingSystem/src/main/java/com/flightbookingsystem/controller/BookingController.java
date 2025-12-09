package com.flightbookingsystem.controller;

import java.time.LocalDateTime;
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
import com.flightbookingsystem.entity.Booking;
import com.flightbookingsystem.entity.Passenger;
import com.flightbookingsystem.entity.Payment;
import com.flightbookingsystem.exception.IdNotFoundException;
import com.flightbookingsystem.service.BookingService;

@RequestMapping("/booking")
@RestController
public class BookingController {
	@Autowired
	private BookingService bookingService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Booking>> addBooking(@RequestBody Booking booking) {
		return bookingService.addBooking(booking);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBooking() {
		return bookingService.getAllBooking();
	}

	@GetMapping("{id}")
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(@PathVariable Integer id) {
		return bookingService.getBookingById(id);
	}

	@GetMapping("/FlightId/{flightId}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlightId(@PathVariable Integer flightId) {
		return bookingService.getBookingByFlightId(flightId);
	}

	@GetMapping("/Date/{bookingDate}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByDate(@PathVariable LocalDateTime bookingDate) {
		return bookingService.getBookingByDate(bookingDate);
	}

	@GetMapping("/Status/{status}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByStatus(@PathVariable Booking.Status status) {
		return bookingService.getBookingByStatus(status);
	}

	@GetMapping("/Payment/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentByBookingId(@PathVariable Integer id) {
		return bookingService.getPaymentByBookingId(id);
	}

	@GetMapping("/Passenger/{id}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerById(@PathVariable Integer id) {
		return bookingService.getPassengerById(id);
	}

	@PutMapping("/{bookingId}/{status}")
	public ResponseEntity<ResponseStructure<Booking>> updateStatusByBooking(@PathVariable Integer bookingId,@PathVariable Booking.Status status ) {
		return bookingService.updateStatusByBooking(bookingId, status);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Booking>> deleteBooking(@PathVariable Integer id){
		return bookingService.deleteBooking(id);
	}
	@GetMapping("/PaginationAndSorting/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Booking>>> GetBookingByPaginationAndSorting(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String field){
		return bookingService.GetBookingByPaginationAndSorting(pageNumber,pageSize,field);
	}


}
