package com.flightbookingsystem.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flightbookingsystem.Dto.ResponseStructure;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(NoRecordFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleNoRecordFoundException(NoRecordFoundException exception) {
		ResponseStructure<String> response=new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Failure");
		response.setData(exception.getMessage());
		return new  ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	  @ExceptionHandler(IdNotFoundException.class)
	    public ResponseEntity<ResponseStructure<String>> handleIdNotFound(IdNotFoundException ex) {
	        ResponseStructure<String> response = new ResponseStructure<>();
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("Failure");
	        response.setData(ex.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	  
	  @ExceptionHandler(InvalidValueException.class)
	    public ResponseEntity<ResponseStructure<String>> handleIdNotFound(InvalidValueException ex) {
	        ResponseStructure<String> response = new ResponseStructure<>();
	        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        response.setMessage("Failure");
	        response.setData(ex.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	
}
