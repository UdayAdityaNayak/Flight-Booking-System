package com.flightbookingsystem.entity;


import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@CreationTimestamp
	private LocalDateTime bookingDate;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToMany(mappedBy = "booking",cascade = CascadeType.ALL)
	private List<Passenger> passenger;
	
	@JoinColumn
	@ManyToOne
	private Flight flight;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Payment payment;
	public enum Status{
        SUCCESS,
        FAILED,
        PENDING
	}
}	
