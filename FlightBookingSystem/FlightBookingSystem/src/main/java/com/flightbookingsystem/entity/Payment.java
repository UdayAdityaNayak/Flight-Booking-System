package com.flightbookingsystem.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@CreationTimestamp
	private LocalDateTime paymentDate;
	private Double amount;
	@Enumerated(EnumType.STRING)
	private Mode mode;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@JoinColumn
	@JsonIgnore
	@OneToOne(mappedBy = "payment")
	private Booking booking;
	
	public enum Mode{
        CREDIT_CARD,
        DEBIT_CARD,
        UPI,
        NET_BANKING,
        CASH
	}
	public enum Status{
        SUCCESS,
        FAILED,
        PENDING
	}
}
