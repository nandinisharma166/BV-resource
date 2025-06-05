package com.example.demo.repository;

import com.example.demo.entity.ResourceMate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourceMateRepository extends MongoRepository<ResourceMate, String> {
    ResourceMate findByEmail(String email);
}
