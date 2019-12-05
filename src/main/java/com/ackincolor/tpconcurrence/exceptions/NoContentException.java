package com.ackincolor.tpconcurrence.exceptions;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;
import com.ackincolor.tpconcurrence.entities.ErrorDefinitionErrors;

public class NoContentException extends CustomException {
    private ErrorDefinition errorDefinition;
    public NoContentException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors errorDefinitionErrors = new ErrorDefinitionErrors("204","Document non trouvé");
        this.errorDefinition.addErrorsItem(errorDefinitionErrors);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
    }
    public ErrorDefinition getErrorDefinition(){
        return this.errorDefinition;
    }
}
