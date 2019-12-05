package com.ackincolor.tpconcurrence.exceptions;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;
import com.ackincolor.tpconcurrence.entities.ErrorDefinitionErrors;
import com.ackincolor.tpconcurrence.entities.Lock;

public class ConflictException extends CustomException {
    private ErrorDefinition errorDefinition;
    public ConflictException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors err = new ErrorDefinitionErrors("409","Conflit lors de la modification");
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
        this.errorDefinition.addErrorsItem(err);
    }
    public ConflictException(String message, Lock l) {
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors err = new ErrorDefinitionErrors("409",message+ l.getOwner());
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
        this.errorDefinition.addErrorsItem(err);
    }
    public ErrorDefinition getErrorDefinition(){
        return this.errorDefinition;
    }
}