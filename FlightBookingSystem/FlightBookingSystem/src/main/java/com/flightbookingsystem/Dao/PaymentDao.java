package com.flightbookingsystem.Dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightbookingsystem.entity.Payment;
import com.flightbookingsystem.repository.PaymentRepository;
@Repository
public class PaymentDao {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public Payment addPayment(Payment payment) {
		return paymentRepository.save(payment);
	}
	public List<Payment> getAllPayments()
	{
		return paymentRepository.findAll();
	}
	
	public Optional<Payment> getPaymentById(Integer id) {
		return paymentRepository.findById(id);
	}
	public List<Payment> getPaymentByStatus(Payment.Status status) {
	    return paymentRepository.findPaymentByStatus(status);
	}
	public List<Payment> getPaymentWhereAmountIsGreaterThenAPerticularValue(Double amount ) {
		return paymentRepository.findByAmountGreaterThan(amount);
	}
	public Page<Payment> getPaymentByPaginationAndSorting(Integer pageNumber,Integer pageSize,String field){
		return paymentRepository.findAll(PageRequest.of(pageNumber,pageSize ,Sort.by(field).ascending()));
	}
	public List<Payment> getPaymentByModeOfTransaction(Payment.Mode mode) {
	    return paymentRepository.findPaymentByMode(mode);
	}
	public Payment updtaePaymentStatus(Payment payment) {
		return paymentRepository.save(payment);
	}
}

