package com.ackincolor.tpconcurrence.entities;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * ErrorDefinitionErrors
 */

public class ErrorDefinitionErrors   {
  @JsonProperty("errorCode")
  private String errorCode = null;

  @JsonProperty("errorMessage")
  private String errorMessage = null;

  public ErrorDefinitionErrors(String code,String error){
    this.errorCode = code;
    this.errorMessage = error;
  }

  public ErrorDefinitionErrors errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * le code de l'erreur
   * @return errorCode
  **/
  @ApiModelProperty(value = "le code de l'erreur")


  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorDefinitionErrors errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * message d'erreur
   * @return errorMessage
  **/
  @ApiModelProperty(value = "message d'erreur")


  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorDefinitionErrors errorDefinitionErrors = (ErrorDefinitionErrors) o;
    return Objects.equals(this.errorCode, errorDefinitionErrors.errorCode) &&
        Objects.equals(this.errorMessage, errorDefinitionErrors.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorCode, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorDefinitionErrors {\n");
    
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

