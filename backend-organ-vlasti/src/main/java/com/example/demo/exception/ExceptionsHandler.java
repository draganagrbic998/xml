package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> handleException(Exception exception){
		if (exception instanceof EmailTakenException) {
			return new ResponseEntity<>(new ExceptionMessage("Email taken!", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		}
		else if (exception instanceof WrongPasswordException) {
			return new ResponseEntity<>(new ExceptionMessage("Wrong password!", HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
		}
		else if (exception instanceof InvalidXMLException) {
			return new ResponseEntity<>(new ExceptionMessage("Invalid XML data!", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		}
		else if (exception instanceof InvalidRDFException) {
			return new ResponseEntity<>(new ExceptionMessage("Invalid RDF data!", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		}
		else if (exception instanceof NotFoundException) {
			return new ResponseEntity<>(new ExceptionMessage("Resource not found!", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
		else if (exception instanceof ResourceTakenException) {
			return new ResponseEntity<>(new ExceptionMessage("Resource can't be created!", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
