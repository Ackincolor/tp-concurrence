package com.ackincolor.tpconcurrence.services;

import com.ackincolor.tpconcurrence.entities.Document;
import com.ackincolor.tpconcurrence.entities.DocumentSummary;
import com.ackincolor.tpconcurrence.entities.DocumentsList;
import com.ackincolor.tpconcurrence.entities.Lock;
import com.ackincolor.tpconcurrence.exceptions.ConflictException;
import com.ackincolor.tpconcurrence.exceptions.NoContentException;
import com.ackincolor.tpconcurrence.exceptions.NotFoundException;
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
        System.out.println(document);
        this.documentRepository.insert(document);
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
        if(l==null&&test!=null){
            l = new Lock();
            l.setLockId(test.getDocumentId());
            l.setOwner(test.getEditor());
            this.lockRepository.save(l);
            if(test.getEtag()>document.getEtag()){
                //raiseException
                try {
                    this.removeLockOnDocument(documentId);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                throw new ConflictException("Une version du document plus récente existe déjà",null);
                //return new ResponseEntity<Document>(document, HttpStatus.CONFLICT);
            }else {
                document.setDocumentId(documentId);
                this.documentRepository.save(document);
                try {
                    this.removeLockOnDocument(documentId);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                return document;
            }
        }else if( l.getOwner().equals(document.getEditor())){
            if(test.getEtag()>document.getEtag()){
                //raiseException
                throw new ConflictException("Une version du document plus récente existe déjà",null);
                //return new ResponseEntity<Document>(document, HttpStatus.CONFLICT);
            }else {
                document.setDocumentId(documentId);
                this.documentRepository.save(document);
                return document;
            }
        }else{
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
    public void removeLockOnDocument(String documentId) throws NotFoundException{
        if(this.lockRepository.existsById(documentId)){
            this.lockRepository.deleteById(documentId);
        }else{
            throw new NotFoundException("Le document n'est pas bloqué");
        }
    }
    public Document findDocumentById(String documentId) throws NotFoundException{
        Document d = this.documentRepository.findDocumentByDocumentId(documentId);
        if(d==null)
            throw new NotFoundException();
        return d;
    }
}
