package com.tms.exception;


import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NotFoundExc.class)
    public ResponseEntity<ExceptionInfo> NotFoundException() {
        return new ResponseEntity<>(new ExceptionInfo
                ("Object is not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionInfo> BadRequestException() {
        return new ResponseEntity<>(new ExceptionInfo("Information is not correct. Check and try again.", HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ExceptionInfo> PSQLException() {
        return new ResponseEntity<>(new ExceptionInfo("Something went wrong. Check your info and try again",
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionInfo> ConstraintViolationException() {
        return new ResponseEntity<>(new ExceptionInfo
                (" Info is not validated. Check and try again ",
                        HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionInfo> ForbiddenException() {
        return new ResponseEntity<>(new ExceptionInfo("You don't have the right to fulfill this request ",
                HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase()), HttpStatus.FORBIDDEN);
    }
}