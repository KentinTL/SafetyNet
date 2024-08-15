package com.openclassrooms.safetynet.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    
	
	  @ExceptionHandler(EntityNotFoundException.class)
	  public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex) {
	    ErrorMessage message = new ErrorMessage(404, "Entity not found");
	    logger.error("Entity not found", ex.getLocalizedMessage());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	  }
	  
	  @ExceptionHandler(EntityAlreadyExistException.class)
	  public ResponseEntity<ErrorMessage> entityAlreadyExistException(EntityAlreadyExistException ex) {
	    ErrorMessage message = new ErrorMessage(410, "Entity already exist");
	    logger.error("Entity already exist", ex.getLocalizedMessage());	    
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.valueOf(410));
	  }
	  
}
