package com.flightbookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer age;
	private String gender;
	private Integer seatNumber;
	private Long contactNumber;

	 
	@JoinColumn
	@JsonIgnore
	@ManyToOne
	private Booking booking;
}
