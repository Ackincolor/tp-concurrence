package com.ackincolor.tpconcurrence.exceptions;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;
import com.ackincolor.tpconcurrence.entities.ErrorDefinitionErrors;
import com.ackincolor.tpconcurrence.entities.Lock;
import org.springframework.http.HttpStatus;

public class ConflictException extends CustomException {
    private ErrorDefinition errorDefinition;
    public ConflictException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors err = new ErrorDefinitionErrors("409","Conflit lors de la modification");
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
        this.errorDefinition.addErrorsItem(err);
        this.httpCode = HttpStatus.CONFLICT;
    }
    public ConflictException(String message, Lock l) {
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors err;
        if(l!=null)
            err = new ErrorDefinitionErrors("409",message+ l.getOwner());
        else
            err = new ErrorDefinitionErrors("409", message);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
        this.errorDefinition.addErrorsItem(err);
        this.httpCode = HttpStatus.CONFLICT;
    }
    public ErrorDefinition getErrorDefinition(){
        return this.errorDefinition;
    }
}
