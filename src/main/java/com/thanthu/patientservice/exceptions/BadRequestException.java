package com.thanthu.patientservice.exceptions;

public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = -5557881000232943396L;

	public BadRequestException(String message) {
        super(message);
    }
	
	public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
