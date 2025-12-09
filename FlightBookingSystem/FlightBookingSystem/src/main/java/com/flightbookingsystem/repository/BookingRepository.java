
package com.flightbookingsystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbookingsystem.entity.Booking;
import com.flightbookingsystem.entity.Booking.Status;

public interface BookingRepository extends JpaRepository<Booking ,Integer> {
	List<Booking> findByFlightId(Integer flightId);

	List<Booking> findByBookingDate(LocalDateTime bookingDate);
	List<Booking> findByStatus(Booking.Status status);
}
