package com.ackincolor.tpconcurrence.entities;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.validation.annotation.Validated;

import static org.springframework.data.mongodb.core.mapping.FieldType.TIMESTAMP;

/**
 * résumé d&#39;un document
 */
@ApiModel(description = "résumé d'un document")

public class DocumentSummary   {

  @Id
  @JsonProperty("documentId")
  protected String documentId = null;

  @CreatedDate
  @JsonProperty("created")
  protected Date created = null;

  @LastModifiedDate
  @JsonProperty("updated")
  protected Date updated = null;

  @JsonProperty("title")
  protected String title = null;

  @Version
  @JsonProperty("Etag")
  protected Long etag;

    public DocumentSummary(String documentId, Date created, Date updated, String title) {
        this.documentId = documentId;
        this.created = created;
        this.updated = updated;
        this.title = title;
    }
    public DocumentSummary(Document doc){
        this.documentId = doc.documentId;
        this.created = doc.created;
        this.updated = doc.updated;
        this.title = doc.title;
    }

    public DocumentSummary documentId(String documentId) {
    this.documentId = documentId;
    return this;
  }

  /**
   * identifiant du document
   * @return documentId
  **/
  @ApiModelProperty(value = "identifiant du document")


  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public DocumentSummary created(Date created) {
    this.created = created;
    return this;
  }

  public DocumentSummary setEtag(Long etag){
    this.etag = etag;
    return this;
  }
  public Long getEtag(){
    return this.etag;
  }

  /**
   * la date de création
   * @return created
  **/
  @ApiModelProperty(value = "la date de création")


  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public DocumentSummary updated(Date updated) {
    this.updated = updated;
    return this;
  }

  /**
   * date de la mise à jour
   * @return updated
  **/
  @ApiModelProperty(value = "date de la mise à jour")


  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public DocumentSummary title(String title) {
    this.title = title;
    return this;
  }

  /**
   * titre du document
   * @return title
  **/
  @ApiModelProperty(value = "titre du document")


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentSummary documentSummary = (DocumentSummary) o;
    return Objects.equals(this.documentId, documentSummary.documentId) &&
        Objects.equals(this.created, documentSummary.created) &&
        Objects.equals(this.updated, documentSummary.updated) &&
        Objects.equals(this.title, documentSummary.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentId, created, updated, title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentSummary {\n");
    
    sb.append("    documentId: ").append(toIndentedString(documentId)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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

