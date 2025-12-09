package com.flightbookingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbookingsystem.Dto.ResponseStructure;
import com.flightbookingsystem.entity.Payment;
import com.flightbookingsystem.entity.Payment.Status;
import com.flightbookingsystem.service.PaymentService;
@RequestMapping("/payment")
@RestController
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Payment>> addPayment(@RequestBody Payment payment){
		return paymentService.addPayment(payment);
	}
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments(){
		return paymentService.getAllPayments();
	}
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(@PathVariable Integer id){
		return paymentService.getPaymentById(id);
	}
	@GetMapping("/status/{status}")
	public  ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(@PathVariable Payment.Status status){
		return paymentService.getPaymentByStatus(status);
	}
	@GetMapping("/PriceGreaterThenValue/{amount}")
	public ResponseEntity<ResponseStructure<List<Payment>>>  getPaymentWhereAmountIsGreaterThenAPerticularValue(@PathVariable double amount){
		return paymentService.getPaymentWhereAmountIsGreaterThenAPerticularValue(amount);
	}
	
	
	@GetMapping("/PaginationAndSorting/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure< Page<Payment>>> getPaymentByPaginationAndSorting(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,@PathVariable String field)
	{
		return paymentService.getPaymentByPaginationAndSorting(pageNumber, pageSize, field);
	}
	@GetMapping("/mode/{mode}")
	public  ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByModeOfTransaction(@PathVariable Payment.Mode mode){
		return paymentService.getPaymentByModeOfTransaction(mode);
	}
	@GetMapping("/UpdateStatus/{id}/{status}")
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(@PathVariable Integer id, @PathVariable Status status){
		return  paymentService.updtaePaymentStatus(id, status);
	}
}
