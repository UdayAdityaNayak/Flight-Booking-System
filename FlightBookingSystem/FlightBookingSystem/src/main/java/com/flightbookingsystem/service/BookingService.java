package com.flightbookingsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightbookingsystem.Dao.BookingDao;
import com.flightbookingsystem.Dto.ResponseStructure;
import com.flightbookingsystem.entity.Booking;
import com.flightbookingsystem.entity.Booking.Status;
import com.flightbookingsystem.entity.Flight;
import com.flightbookingsystem.entity.Passenger;
import com.flightbookingsystem.entity.Payment;
import com.flightbookingsystem.exception.IdNotFoundException;
import com.flightbookingsystem.exception.NoRecordFoundException;
import com.flightbookingsystem.repository.FlightRepository;

@Service
public class BookingService {
	@Autowired
	private BookingDao bookingDao;
	@Autowired
	private FlightRepository flightRepository;

	public ResponseEntity<ResponseStructure<Booking>> addBooking(Booking booking) {
	    if (booking == null || booking.getFlight() == null || booking.getFlight().getId() == null) {
	        throw new IdNotFoundException("Flight id must be provided in the booking payload");
	    }

	    Integer flightId = booking.getFlight().getId();
	    Optional<Flight> fl = flightRepository.findById(flightId);
	    if (fl.isPresent()) {
	        booking.setFlight(fl.get());
	    } else {
	        throw new IdNotFoundException("flight not found in the database");
	    }

	    if (booking.getPassenger() == null||booking.getPassenger().isEmpty()) {
	    	throw new NoRecordFoundException("Passenegr details must be given :");
	        
	    } else {
	        for (Passenger p : booking.getPassenger()) {
	            p.setBooking(booking);
	        }
	    }

	    int passengerCount = booking.getPassenger().size();
	    double totalPrice = passengerCount * fl.get().getPrice();

	    if (booking.getPayment() == null) {
	        booking.setPayment(new Payment());
	    }
	    
	    booking.getPayment().setAmount(totalPrice);

	    if (booking.getPayment().getMode() == null || booking.getPayment().getStatus() == null) {
	        ResponseStructure<Booking> err = new ResponseStructure<>();
	        err.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        err.setMessage("Payment details  should be passed :");
	        err.setData(null);
	        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	    }
	    ResponseStructure<Booking> response = new ResponseStructure<Booking>();
	    response.setStatusCode(HttpStatus.CREATED.value());
	    response.setMessage("Booking record is saved");
	    response.setData(bookingDao.addBooking(booking));
	    return new ResponseEntity<ResponseStructure<Booking>>(response, HttpStatus.CREATED);
	}

	

	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBooking() {
		ResponseStructure<List<Booking>> response = new ResponseStructure<>();
		List<Booking> lb = bookingDao.getAllBooking();
		if (!lb.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All the Booking record is fetched successfuly");
			response.setData(lb);
			return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);
		}

		else {
			throw new NoRecordFoundException("No records present in Booking");
		}
	}

	public ResponseEntity<ResponseStructure<Booking>> getBookingById(Integer id) {
		ResponseStructure<Booking> response = new ResponseStructure<Booking>();
		Optional<Booking> opt = bookingDao.getBookingById(id);
		if (opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking record is fetched successfuly by this id");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Booking>>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("ID is not found");
		}

	}

	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlightId(Integer flightId) {
		ResponseStructure<List<Booking>> response = new ResponseStructure<>();
		List<Booking> lb = bookingDao.getBookingByFlightId(flightId);
		if (!lb.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking record is fetched successfuly by given flight id");
			response.setData(lb);
			return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Unable to fetched Booking record there is no flight with this id");
		}

	}

	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByDate(LocalDateTime bookingDate) {
		ResponseStructure<List<Booking>> response = new ResponseStructure<List<Booking>>();
		List<Booking> lb = bookingDao.getBookingByDate(bookingDate);
		if (!lb.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking record is fetched successfuly by given date id");
			response.setData(lb);
			return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("There is no Booking record  in the given date");
		}
	}

	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByStatus(Booking.Status status) {
		ResponseStructure<List<Booking>> response = new ResponseStructure<List<Booking>>();
		List<Booking> lb = bookingDao.getBookingByStatus(status);
		if (!lb.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking record is fetched successfuly as per the status");
			response.setData(lb);
			return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("No Booking record with the given status ");
		}
	}

	public ResponseEntity<ResponseStructure<Payment>> getPaymentByBookingId(Integer id) {
		ResponseStructure<Payment> response = new ResponseStructure<>();
		Optional<Booking> opt = bookingDao.getPaymentByBookingId(id);
		if (opt.isPresent()) {
			Booking b = opt.get();
			Payment p = b.getPayment();
			if (p != null) {
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("Payment record is fetched successfuly based on the given booking id");
				response.setData(p);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				throw new NoRecordFoundException("No Payment details found in the database");
			}
		} else {
			throw new IdNotFoundException("There is no booking with the given id");
		}
	}

	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerById(Integer id) {
		ResponseStructure<List<Passenger>> response = new ResponseStructure<>();
		Optional<Booking> opt = bookingDao.getPaymentByBookingId(id);
		if (opt.isPresent()) {
			Booking b = opt.get();
			List<Passenger> lp = b.getPassenger();
			if (!lp.isEmpty()) {
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("All Passenger record is fetched successfuly based on the given booking id");
				response.setData(lp);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				throw new NoRecordFoundException("No Passenger details found in the database");
			}
		} else {
			throw new IdNotFoundException("There is no booking with the given id");
		}
	}

	public ResponseEntity<ResponseStructure<Booking>> updateStatusByBooking(Integer bookingId, Status status) {
		ResponseStructure<Booking> response = new ResponseStructure<>();
		if (bookingId == null) {
			throw new IdNotFoundException("Id of booking must be provided");
		} else {
			Optional<Booking> opt = bookingDao.getBookingById(bookingId);
			if (opt.isPresent()) {
				Booking booking = opt.get();
				booking.setStatus(status);
				Booking updated = bookingDao.updateBooking(booking);
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("The status is updated successfully  based on the given booking id");
				response.setData(updated);
				return new ResponseEntity<ResponseStructure<Booking>>(response, HttpStatus.OK);
			} 
			else {
				throw new NoRecordFoundException("There is no record with the geiven id");
			}
		}

	}
	public ResponseEntity<ResponseStructure<Booking>> deleteBooking(Integer id){
		ResponseStructure<Booking> response = new ResponseStructure<>();
		Optional<Booking> opt = bookingDao.getBookingById(id);
		if (opt.isPresent()) {
			bookingDao.deleteBooking(opt.get());
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking record is deleted successfuly with the given id");
			response.setData(null);
			return new ResponseEntity<ResponseStructure<Booking>>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("ID is not found");
		}
		
	}
	public ResponseEntity<ResponseStructure<Page<Booking>>> GetBookingByPaginationAndSorting(Integer pageNumber,Integer pageSize,String field){
		Page<Booking> lb=bookingDao.GetBookingByPaginationAndSorting(pageNumber, pageSize, field);
		ResponseStructure<Page<Booking>> response = new ResponseStructure<>();
		if(!lb.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All the record is fetched successfully with page number size in sorting");
			response.setData(lb);
			return new ResponseEntity<ResponseStructure<Page<Booking>>>(response,HttpStatus.OK);
		}
		else {
			throw new NoRecordFoundException("No record is found with the given credencials  ");
		}
	}
}
