package com.openclassrooms.safetynet.exceptions;

public class EntityAlreadyExistException extends RuntimeException {
	
	private String message;
	
	public EntityAlreadyExistException(String message) {
		super(message);
	}
	
}
