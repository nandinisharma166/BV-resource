package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ResourceMate")
public class ResourceMate {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;

}
