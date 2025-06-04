package com.example.demo.repository;

import com.example.demo.entity.UploadedResource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UploadedResourceRepository extends MongoRepository<UploadedResource, String> {
    List<UploadedResource> findByUploadedByEmail(String email);
}
