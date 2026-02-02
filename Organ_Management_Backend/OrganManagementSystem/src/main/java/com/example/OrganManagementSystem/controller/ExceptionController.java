package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UnauthorisedUserException.class)
    public ResponseEntity<Object> unauthorisedException(UnauthorisedUserException unauthorisedUserException){
        return new ResponseEntity<>("Unauthorised to access this URL", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(PatientNotFoundException userNotFoundException){
        return new ResponseEntity<>("User not found for given id", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Object> doctorNotFoundException(DoctorNotFoundException doctorNotFoundException){
        return new ResponseEntity<>("Doctor not found for given id", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DonorNotFoundException.class)
    public ResponseEntity<Object> donorNotFoundException(DonorNotFoundException donorNotFoundException){
        return new ResponseEntity<>("Donor not found for given id", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecipientNotFoundException.class)
    public ResponseEntity<Object> recipientNotFoundException(RecipientNotFoundException recipientNotFoundException){
        return new ResponseEntity<>("Recipient not found for given id", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<Object> matchNotFoundException(MatchNotFoundException matchNotFoundException){
        return new ResponseEntity<>("Added but currently no matches found/available", HttpStatus.OK);
    }
}

