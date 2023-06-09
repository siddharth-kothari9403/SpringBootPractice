package com.example.restDemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//global exception handler accessed by all rest controllers
@ControllerAdvice
public class StudentRestExceptionHandler {
    //the handler to handle the custom exception
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){
        StudentErrorResponse err = new StudentErrorResponse();
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setMessage(exc.getMessage());
        err.setTimestamp(System.currentTimeMillis());

        //err is the body, the other argument is the status
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    //handle generic errors
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleGeneralException(Exception exc){
        StudentErrorResponse err = new StudentErrorResponse();

        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setMessage(exc.getMessage());
        err.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
