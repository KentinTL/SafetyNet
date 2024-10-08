package com.openclassrooms.safetynet.exceptions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex) {
		ErrorMessage message = new ErrorMessage(404, "Entity not found");
		logger.warn("Warning: Entity not found for request: {}", ex.getMessage()); // Logging en niveau WARN
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityAlreadyExistException.class)
	public ResponseEntity<ErrorMessage> entityAlreadyExistException(EntityAlreadyExistException ex) {
		ErrorMessage message = new ErrorMessage(410, "Entity already exists");
		logger.info("Info: Attempt to create an existing entity: {}", ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.GONE);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
		ErrorMessage message = new ErrorMessage(400, "Invalid input: " + ex.getMessage());
		logger.error("Error: Invalid argument received: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleMethodeArugmentNotValidException(MethodArgumentNotValidException ex) {
	    logger.error("Validation Error: {}", ex.getMessage(), ex);
	    
	    BindingResult result = ex.getBindingResult();
	    var errors = result.getFieldErrors();

	    var message = processFieldError(errors);
	    
	    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
		ErrorMessage message = new ErrorMessage(500, "Internal server error: " + ex.getMessage());
		logger.error("Critical Error: Unexpected error occurred: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorMessage processFieldError(List<FieldError> error) {
		String message = null;
		if (error != null) {
			var errorStream = error.stream().map(t -> t.getDefaultMessage()).toList();
			message = String.join(", ", errorStream);
		}
		return new ErrorMessage(400, message);
	}

}
