package com.ackincolor.tpconcurrence.repositories;

import com.ackincolor.tpconcurrence.entities.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface DocumentRepository extends MongoRepository<Document,String> {
    public Document findDocumentByDocumentId(String documentId);

}
