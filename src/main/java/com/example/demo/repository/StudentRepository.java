package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    boolean existsById(String id); // check if ID already exists
}
