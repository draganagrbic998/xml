package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler
	public ResponseEntity<Void> handleException(Exception exception){
		exception.printStackTrace();
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
