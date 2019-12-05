package com.ackincolor.tpconcurrence.exceptions;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;
import org.springframework.http.HttpStatus;

public class CustomException extends Exception {
    protected ErrorDefinition errorDefinition;
    protected HttpStatus httpCode;
    public ErrorDefinition getErrorDefinition(){
        return this.errorDefinition;
    }
    public HttpStatus getHttpCode(){
        return httpCode;
    }
}
