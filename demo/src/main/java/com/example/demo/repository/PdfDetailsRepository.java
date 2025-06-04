package com.example.demo.repository;

import com.example.demo.entity.PdfDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PdfDetailsRepository extends MongoRepository<PdfDetails, String> {

//    List<PdfDetails> findByType(String type);

}

