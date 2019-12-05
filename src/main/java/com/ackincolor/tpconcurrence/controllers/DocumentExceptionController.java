package com.ackincolor.tpconcurrence.controllers;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;
import com.ackincolor.tpconcurrence.exceptions.ConflictException;
import com.ackincolor.tpconcurrence.exceptions.CustomException;
import com.ackincolor.tpconcurrence.exceptions.NoContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DocumentExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ConflictException.class,NoContentException.class})
    protected ResponseEntity<ErrorDefinition> conflictExceptionHandler(CustomException e){
        return new ResponseEntity<>(e.getErrorDefinition(), e.getHttpCode());
    }
}
