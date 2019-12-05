package com.ackincolor.tpconcurrence.exceptions;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;

public class CustomException extends Exception {
    protected ErrorDefinition errorDefinition;
    public ErrorDefinition getErrorDefinition(){
        return this.errorDefinition;
    }
}
