package com.example.demo.entity;

import lombok.Data;
//import org.hibernate.annotations.processing.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "students")
public class Student {

    @Id
    private String collegeId;
    private int yearOfGraduation;

    public Student() {}

}
