package com.ackincolor.tpconcurrence.services;

import com.ackincolor.tpconcurrence.entities.Document;
import com.ackincolor.tpconcurrence.entities.DocumentSummary;
import com.ackincolor.tpconcurrence.entities.DocumentsList;
import com.ackincolor.tpconcurrence.entities.Lock;
import com.ackincolor.tpconcurrence.exceptions.ConflictException;
import com.ackincolor.tpconcurrence.exceptions.NoContentException;
import com.ackincolor.tpconcurrence.repositories.DocumentRepository;
import com.ackincolor.tpconcurrence.repositories.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentsService {

    @Autowired
    public DocumentRepository documentRepository;
    @Autowired
    public LockRepository lockRepository;


    public Document saveDocument(Document document){
        UUID uuid = UUID.randomUUID();
        document.setDocumentId(uuid.toString());
        document.setEtag(0);
        this.documentRepository.save(document);
        return  document;
    }
    public DocumentsList findAll() {
        List<Document> liste = this.documentRepository.findAll();
        List<DocumentSummary> liste2 = new ArrayList<>();
        for(Document doc : liste){
            liste2.add(new DocumentSummary(doc));
        }
        DocumentsList documentsList = new DocumentsList();
        documentsList.data(liste2);
        return documentsList;
    }
    public Document updateDocument(Document document, String documentId) throws ConflictException{
        Document test = this.documentRepository.findDocumentByDocumentId(documentId);
        Lock l = this.lockRepository.findByLockId(documentId);
        //Lock l = this.lockList.get(documentId);
        //si l'editeur a posé un verou
        if((l==null || l.getOwner().equals(document.getEditor()))&&test!=null) {
            //mise a jour avec verification de la version
            if(test.getEtag()>document.getEtag()){
                //raiseException
                throw new ConflictException();
                //return new ResponseEntity<Document>(document, HttpStatus.CONFLICT);
            }else {
                document.setUpdated(new Date(System.currentTimeMillis()));
                document.setDocumentId(documentId);
                document.setEtag(document.getEtag()+1);
                this.documentRepository.save(document);
                return document;
            }
        }else{
            //raise exception
            throw new ConflictException();
        }
    }
    public Lock lockDocument(String documentId,Lock lock) throws ConflictException, NoContentException {
        if(this.documentRepository.findDocumentByDocumentId(documentId)!=null){
            Lock l =this.lockRepository.findByLockId(documentId);
            if(l!=null)
                throw new ConflictException("Document déjà verouillé par : ",l);
            lock.created(new Date(System.currentTimeMillis()));
            lock.setLockId(documentId);
            l =this.lockRepository.save(lock);
            //Lock l = this.lockList.put(documentId,lock);
            if(l!=null){
                return l;
            }else{
                throw new ConflictException();
            }
            //return (l==null)?new ResponseEntity<Lock>(lock,HttpStatus.OK) :new ResponseEntity<Lock>(lock, HttpStatus.CONFLICT);
        }else {
            throw new NoContentException();
            //return new ResponseEntity<Lock>(lock,HttpStatus.NO_CONTENT);
        }
    }
    public Lock getLockOfDocument(String documentId)throws NoContentException{
        Lock l = this.lockRepository.findByLockId(documentId);
        if(l==null)
            throw new NoContentException();
        return l;
    }
    public void removeLockOnDocument(String documentId) throws NoContentException{
        if(this.lockRepository.existsById(documentId)){
            this.lockRepository.deleteById(documentId);
        }else{
            throw new NoContentException();
        }
    }
    public Document findDocumentById(String documentId) throws NoContentException{
        Document d = this.documentRepository.findDocumentByDocumentId(documentId);
        if(d==null)
            throw new NoContentException();
        return d;
    }
}
