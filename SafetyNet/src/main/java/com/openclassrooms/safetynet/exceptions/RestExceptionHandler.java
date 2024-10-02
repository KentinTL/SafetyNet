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

    // Gestion d'exception spécifique: EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(404, "Entity not found");
        logger.warn("Warning: Entity not found for request: {}", ex.getMessage());  // Logging en niveau WARN
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    // Gestion d'exception spécifique: EntityAlreadyExistException
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> entityAlreadyExistException(EntityAlreadyExistException ex) {
        ErrorMessage message = new ErrorMessage(410, "Entity already exists");
        logger.info("Info: Attempt to create an existing entity: {}", ex.getMessage());  // Logging en niveau INFO
        return new ResponseEntity<>(message, HttpStatus.GONE);
    }

    // Gestion d'exception spécifique: IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorMessage message = new ErrorMessage(400, "Invalid input: " + ex.getMessage());
        logger.error("Error: Invalid argument received: {}", ex.getMessage(), ex);  // Logging en niveau ERROR
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    // Gestion d'exception globale: Exception (attrape toutes les erreurs non gérées)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
        ErrorMessage message = new ErrorMessage(500, "Internal server error: " + ex.getMessage());
        logger.error("Critical Error: Unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
