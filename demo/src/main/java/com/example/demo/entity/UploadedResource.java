package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "uploaded_resources")
public class UploadedResource {


    private String subject;
    private String category;
    private String branch;
    private String semester;
    private String filePath;
    private String description;

    private LocalDateTime uploadedAt = LocalDateTime.now();

    private String uploadedByEmail; // We'll store email instead of linking whole object

    // Getters and Setters
}
