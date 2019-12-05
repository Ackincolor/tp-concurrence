package com.ackincolor.tpconcurrence.entities;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

/**
 * a document
 */
@ApiModel(description = "a document")

public class Document extends DocumentSummary {

  private String creator;
  private String editor;
  private String body;

  public Document(String documentId, Date created, Date updated, String title, String creator, String editor, String body) {
    super(documentId,created,updated,title);
    this.creator = creator;
    this.editor = editor;
    this.body = body;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getEditor() {
    return editor;
  }

  public void setEditor(String editor) {
    this.editor = editor;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String toString(){
    return this.documentId+" : version :"+this.getEtag();
  }
}

