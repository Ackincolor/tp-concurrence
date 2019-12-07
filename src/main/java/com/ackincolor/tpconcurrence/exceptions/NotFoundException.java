package com.ackincolor.tpconcurrence.exceptions;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;
import com.ackincolor.tpconcurrence.entities.ErrorDefinitionErrors;
import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {
    public NotFoundException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors errorDefinitionErrors = new ErrorDefinitionErrors("404","Document non trouvé");
        this.httpCode = HttpStatus.NOT_FOUND;
        this.errorDefinition.addErrorsItem(errorDefinitionErrors);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
    }
    public NotFoundException(String msg){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors errorDefinitionErrors = new ErrorDefinitionErrors("404",msg);
        this.httpCode = HttpStatus.NOT_FOUND;
        this.errorDefinition.addErrorsItem(errorDefinitionErrors);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
    }
}
