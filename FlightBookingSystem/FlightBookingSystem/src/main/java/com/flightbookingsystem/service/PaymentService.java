package com.flightbookingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightbookingsystem.Dao.PaymentDao;
import com.flightbookingsystem.Dto.ResponseStructure;
import com.flightbookingsystem.entity.Payment;
import com.flightbookingsystem.entity.Payment.Status;
import com.flightbookingsystem.exception.IdNotFoundException;
import com.flightbookingsystem.exception.InvalidValueException;
import com.flightbookingsystem.exception.NoRecordFoundException;

@Service
public class PaymentService {
	@Autowired
	private PaymentDao paymentDao;

	public ResponseEntity<ResponseStructure<Payment>> addPayment(Payment payment) {
		ResponseStructure<Payment> response = new ResponseStructure<Payment>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Payment record is saved");
		response.setData(paymentDao.addPayment(payment));
		return new ResponseEntity<ResponseStructure<Payment>>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments() {
		ResponseStructure<List<Payment>> response = new ResponseStructure<>();
		List<Payment> pl = paymentDao.getAllPayments();
		if (!pl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All Payment record is fatched successfully");
			response.setData(pl);
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response, HttpStatus.OK);
		} else {
			throw new NoRecordFoundException("There is no payment record found in the database");

		}

	}

	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(Integer id) {
		ResponseStructure<Payment> response = new ResponseStructure<Payment>();
		Optional<Payment> opt = paymentDao.getPaymentById(id);
		if (opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Payment info is fatched successfully using the given id");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Payment>>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("There is no payment is record with this id");
		}

	}

	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(Payment.Status status) {
		ResponseStructure<List<Payment>> response = new ResponseStructure<>();
		List<Payment> pl = paymentDao.getPaymentByStatus(status);
		if (!pl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Payment info is fatched successfully using the given status");
			response.setData(pl);
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response, HttpStatus.OK);
		} else {
			throw new NoRecordFoundException("There is no payment is record with this status");
		}
	}

	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentWhereAmountIsGreaterThenAPerticularValue(
			double amount) {
		ResponseStructure<List<Payment>> response = new ResponseStructure<>();
		List<Payment> pl = paymentDao.getPaymentWhereAmountIsGreaterThenAPerticularValue(amount);
		if (!pl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Payment info is fatched successfully which are greater then the given value");
			response.setData(pl);
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response, HttpStatus.OK);
		} else {
			throw new NoRecordFoundException("There is no payment is record which is greather then the given value");
		}
	}

	public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentByPaginationAndSorting(Integer pageNumber,
			Integer pageSize, String field) {
		ResponseStructure<Page<Payment>> response = new ResponseStructure<>();
		Page<Payment> pl = paymentDao.getPaymentByPaginationAndSorting(pageNumber, pageSize, field);
		if (!pl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All Payment info is fatched successfully using pagination and sorting");
			response.setData(pl);
			return new ResponseEntity<ResponseStructure<Page<Payment>>>(response, HttpStatus.OK);
		} else {
			throw new NoRecordFoundException(
					"There is no record found in the databse due to the paginationa or field of sorting");
		}

	}

	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByModeOfTransaction(Payment.Mode mode) {
		ResponseStructure<List<Payment>> response = new ResponseStructure<>();
		List<Payment> pl = paymentDao.getPaymentByModeOfTransaction(mode);
		if (!pl.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Payment info is fatched successfully which are from the given mode of transection");
			response.setData(pl);
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response, HttpStatus.OK);
		} else {
			throw new NoRecordFoundException("There is no payment is record with this mode of transection");
		}
	}

	public ResponseEntity<ResponseStructure<Payment>> updtaePaymentStatus(Integer id,Status status){
		ResponseStructure<Payment> response = new ResponseStructure<Payment>();
		Optional<Payment> opt= paymentDao.getPaymentById(id);
		if(opt.isPresent()) {
	       try {
	    	   Payment payment = opt.get();
		        payment.setStatus(status);
		      	response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("Payment status is updated successfully");
				response.setData(paymentDao.addPayment(payment));
				return new ResponseEntity<ResponseStructure<Payment>>(response, HttpStatus.OK);
	       }
	       
	       catch(IllegalArgumentException e) {
	    	   e.printStackTrace();
	       }
			
		}
		else {
			throw new IdNotFoundException("Unable to update the status as is tbe id is not found");
		}
		return null;


	}

}
