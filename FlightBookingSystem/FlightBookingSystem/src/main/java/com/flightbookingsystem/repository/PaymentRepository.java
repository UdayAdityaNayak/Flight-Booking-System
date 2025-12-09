package com.flightbookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbookingsystem.entity.Payment;
import com.flightbookingsystem.entity.Payment.Mode;
import com.flightbookingsystem.entity.Payment.Status;


public interface PaymentRepository extends JpaRepository<Payment ,Integer>{

	List<Payment> findPaymentByStatus(Payment.Status status);

	List<Payment> findByAmountGreaterThan(Double amount);

	List<Payment> findPaymentByMode(Payment.Mode mode);


}
