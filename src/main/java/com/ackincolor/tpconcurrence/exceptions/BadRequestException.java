package com.ackincolor.tpconcurrence.exceptions;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;
import com.ackincolor.tpconcurrence.entities.ErrorDefinitionErrors;
import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {
    public BadRequestException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors errorDefinitionErrors = new ErrorDefinitionErrors("400","Requete mal formée");
        this.httpCode = HttpStatus.BAD_REQUEST;
        this.errorDefinition.addErrorsItem(errorDefinitionErrors);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.TECHNICAL);
    }
}
