package com.flexpag.paymentscheduler.exception;

import com.flexpag.paymentscheduler.constants.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(
                AppConstants.ENTITY_NOT_FOUND,
                LocalDateTime.now(),
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex) {
        ErrorMessage message = new ErrorMessage(
                AppConstants.ENTITY_ERROR,
                LocalDateTime.now(),
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotSavedException.class)
    public ResponseEntity<ErrorMessage> entityNotSavedException(EntityNotSavedException ex) {
        ErrorMessage message = new ErrorMessage(
                AppConstants.ENTITY_NOT_SAVED,
                LocalDateTime.now(),
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityUpdateException.class)
    public ResponseEntity<ErrorMessage> entityUpdateException(EntityUpdateException ex) {
        ErrorMessage message = new ErrorMessage(
                AppConstants.ENTITY_NOT_UPDATED,
                LocalDateTime.now(),
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityDeleteException.class)
    public ResponseEntity<ErrorMessage> entityDeleteException(EntityDeleteException ex) {
        ErrorMessage message = new ErrorMessage(
                AppConstants.ENTITY_NOT_DELETED,
                LocalDateTime.now(),
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

}
