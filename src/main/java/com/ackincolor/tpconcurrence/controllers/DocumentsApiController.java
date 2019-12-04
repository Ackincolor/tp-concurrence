package com.ackincolor.tpconcurrence.controllers;


import com.ackincolor.tpconcurrence.repositories.DocumentRepository;
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

    public ResponseEntity<Document> documentsDocumentIdGet(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Document>(this.documentRepository.findDocumentByDocumentId(documentId),HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> documentsDocumentIdLockDelete(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Lock l = this.lockList.remove(documentId);
            return (l==null)? new ResponseEntity<Void>(HttpStatus.NO_CONTENT) :  new ResponseEntity<Void>(HttpStatus.OK) ;
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockGet(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Lock l = this.lockList.get(documentId);
                return (l==null)? new ResponseEntity<Lock>(new Lock(), HttpStatus.NO_CONTENT) :  new ResponseEntity<Lock>(l, HttpStatus.OK) ;
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockPut(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "l'objet verrou posé"  )  @RequestBody Lock lock) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                if(this.documentRepository.findDocumentByDocumentId(documentId)!=null){
                    lock.created(new Date(System.currentTimeMillis()));
                    Lock l = this.lockList.put(documentId,lock);
                    return (l==null)?new ResponseEntity<Lock>(lock,HttpStatus.OK) :new ResponseEntity<Lock>(lock, HttpStatus.CONFLICT);
                }else {
                    return new ResponseEntity<Lock>(lock,HttpStatus.NO_CONTENT);
                }
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Document> documentsDocumentIdPost(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "met à jour le texte, le titre, l'editeur et la date de mise à jour"  )  @RequestBody Document document) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Document test = this.documentRepository.findDocumentByDocumentId(documentId);
                Lock l = this.lockList.get(documentId);
                //si l'editeur a posé un verou
                if((l==null || l.getOwner().equals(document.getEditor()))&&test!=null) {
                    //mise a jour avec verification de la version
                    if(test.getEtag()>document.getEtag()){
                        return new ResponseEntity<Document>(document, HttpStatus.CONFLICT);
                    }else {
                        document.setUpdated(new Date(System.currentTimeMillis()));
                        this.documentRepository.save(document);
                        return new ResponseEntity<Document>(document, HttpStatus.OK);
                    }
                }else{
                    return new ResponseEntity<Document>(document,HttpStatus.CONFLICT);
                }
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DocumentsList> documentsGet(@ApiParam(value = "numéro de la page à retourner") @RequestParam(value = "page", required = false) Integer page, @ApiParam(value = "nombre de documents par page") @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                //recuperation de tous les docs
                List<Document> liste = this.documentRepository.findAll();
                List<DocumentSummary> liste2 = new ArrayList<>();
                for(Document doc : liste){
                    liste2.add(new DocumentSummary(doc));
                }
                DocumentsList documentsList = new DocumentsList();
                documentsList.data(liste2);
                return new ResponseEntity<DocumentsList>(documentsList, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<DocumentsList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Document> documentsPost(@ApiParam(value = "" ,required=true ) @RequestBody Document document) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                //creation du document
                UUID uuid = UUID.randomUUID();
                document.setDocumentId(uuid.toString());
                document.setCreated(new Date(System.currentTimeMillis()));
                document.setUpdated(new Date(System.currentTimeMillis()));
                document.setEtag(0);
                this.documentRepository.save(document);
                return new ResponseEntity<Document>(document, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Document>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

}

