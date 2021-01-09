package com.example.demo.common;

@SuppressWarnings("serial")
public class MyException extends RuntimeException {

	private Exception exception;

	public MyException(Exception exception) {
		super();
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
}
