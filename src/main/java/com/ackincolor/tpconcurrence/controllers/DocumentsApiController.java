package com.ackincolor.tpconcurrence.controllers;


import com.ackincolor.tpconcurrence.exceptions.ConflictException;
import com.ackincolor.tpconcurrence.exceptions.NoContentException;
import com.ackincolor.tpconcurrence.exceptions.NotFoundException;
import com.ackincolor.tpconcurrence.repositories.DocumentRepository;
import com.ackincolor.tpconcurrence.services.DocumentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;
import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;

import com.ackincolor.tpconcurrence.services.DocumentsApi;
import com.ackincolor.tpconcurrence.entities.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "*")
public class DocumentsApiController implements DocumentsApi {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentsService documentsService;

    private HashMap<String,Lock> lockList;

    private static final Logger log = LoggerFactory.getLogger(DocumentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DocumentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.lockList = new HashMap<>();
    }

    public ResponseEntity<Document> documentsDocumentIdGet(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) throws NotFoundException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<>( this.documentsService.findDocumentById(documentId),HttpStatus.OK);
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> documentsDocumentIdLockDelete(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) throws NoContentException{
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            this.documentsService.removeLockOnDocument(documentId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockGet(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) throws NoContentException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
                return new ResponseEntity<>( this.documentsService.getLockOfDocument(documentId),HttpStatus.OK);
        }
        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockPut(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "l'objet verrou posé"  )  @RequestBody Lock lock) throws ConflictException, NoContentException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<Lock>(this.documentsService.lockDocument(documentId,lock),HttpStatus.OK);
        }
        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Document> documentsDocumentIdPost(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "met à jour le texte, le titre, l'editeur et la date de mise à jour"  )  @RequestBody Document document) throws ConflictException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<Document>(this.documentsService.updateDocument(document,documentId), HttpStatus.OK);
        }
        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DocumentsList> documentsGet(@ApiParam(value = "numéro de la page à retourner") @RequestParam(value = "page", required = false) Integer page, @ApiParam(value = "nombre de documents par page") @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                //recuperation de tous les docs
                return new ResponseEntity<DocumentsList>(this.documentsService.findAll(), HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<DocumentsList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Document> documentsPost(@ApiParam(value = "Document" ,required=true ) @RequestBody Document document) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Document>(this.documentsService.saveDocument(document), HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

}

