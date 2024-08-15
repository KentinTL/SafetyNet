package com.openclassrooms.safetynet.exceptions;

public class EntityNotFoundException extends RuntimeException {

	private String message;
	
	public EntityNotFoundException(String message) {
		super(message);
	}
	
}
