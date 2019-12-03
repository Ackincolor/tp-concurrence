package com.ackincolor.tpconcurrence.services;


import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

import com.ackincolor.tpconcurrence.entities.*;

@Api(value = "documents", description = "the documents API")
public interface DocumentsApi {

    //ok
    @ApiOperation(value = "lis le document", nickname = "documentsDocumentIdGet", notes = "retourne un document", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le document demandé", response = Document.class),
            @ApiResponse(code = 404, message = "le document n'existe pas", response = ErrorDefinition.class) })
    @RequestMapping(value = "/documents/{documentId}",
            method = RequestMethod.GET)
    ResponseEntity<Document> documentsDocumentIdGet(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId);


    @ApiOperation(value = "supprime le verrou posé", nickname = "documentsDocumentIdLockDelete", notes = "", tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "le verrou est supprimé") })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> documentsDocumentIdLockDelete(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId);


    @ApiOperation(value = "retourne le verrou posé sur le document si présent", nickname = "documentsDocumentIdLockGet", notes = "", response = Lock.class, tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le verrou posé", response = Lock.class),
            @ApiResponse(code = 204, message = "aucun verrou posé") })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.GET)
    ResponseEntity<Lock> documentsDocumentIdLockGet(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId);


    @ApiOperation(value = "pose un verrou sur le document", nickname = "documentsDocumentIdLockPut", notes = "l'utilisateur peut poser un verrou si aucun n'autre n'est posé ", response = Lock.class, tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le verrou est posé", response = Lock.class),
            @ApiResponse(code = 409, message = "un verrou est déjà posé, retourne le verrou déjà posé", response = Lock.class) })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.PUT)
    ResponseEntity<Lock> documentsDocumentIdLockPut(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "l'objet verrou posé"  )  @Valid @RequestBody Lock lock);

    //ok
    @ApiOperation(value = "mise à jour du document", nickname = "documentsDocumentIdPost", notes = "met à jour le document si l'utilisateur possède la dernière version et que personne n'a posé de verrou ", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le document est mis à jour", response = Document.class) })
    @RequestMapping(value = "/documents/{documentId}",
            method = RequestMethod.POST)
    ResponseEntity<Document> documentsDocumentIdPost(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "met à jour le texte, le titre, l'editeur et la date de mise à jour"  )  @Valid @RequestBody Document document);

    //ok
    @ApiOperation(value = "retourne tous les documents, pas de filtrage", nickname = "documentsGet", notes = "", response = DocumentsList.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "la liste des documents", response = DocumentsList.class) })
    @RequestMapping(value = "/documents",
            method = RequestMethod.GET)
    ResponseEntity<DocumentsList> documentsGet(@ApiParam(value = "numéro de la page à retourner") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "nombre de documents par page") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize);


    //ok
    @ApiOperation(value = "create a document", nickname = "documentsPost", notes = "", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "le document créé", response = Document.class),
            @ApiResponse(code = 400, message = "le contenu n'est pas correction", response = ErrorDefinition.class) })
    @RequestMapping(value = "/documents",
            method = RequestMethod.POST)
    ResponseEntity<Document> documentsPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Document document);

}
