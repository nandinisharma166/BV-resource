package com.example.demo.controller;

import com.example.demo.entity.UploadedResource;
import com.example.demo.repository.UploadedResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
class ResourceUploadController {

    @Autowired
    private UploadedResourceRepository uploadedRepo;

    // Endpoint for file upload
    // Endpoint for file upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subject") String subject,
            @RequestParam("category") String category,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester,
            @RequestParam("description") String description,
            @RequestParam("email") String uploadedByEmail) {

        // Define the file path where the file will be saved
        String filePath = "uploads/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File destinationFile = new File(filePath);

        // Save the file to the disk
        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }

        // Create UploadedResource object
        UploadedResource uploadedResource = new UploadedResource();
        uploadedResource.setSubject(subject);
        uploadedResource.setCategory(category);
        uploadedResource.setBranch(branch);
        uploadedResource.setSemester(semester);
        uploadedResource.setDescription(description);
        uploadedResource.setFilePath(filePath);
        uploadedResource.setUploadedByEmail(uploadedByEmail);

        // Save to MongoDB
        uploadedRepo.save(uploadedResource);

        return ResponseEntity.ok("File uploaded successfully");
    }

    // Endpoint to get all uploads of a user by email
    @GetMapping("/my-uploads")
    public ResponseEntity<List<UploadedResource>> getMyUploads(@RequestParam("email") String email) {
        List<UploadedResource> uploads = uploadedRepo.findByUploadedByEmail(email);
        return ResponseEntity.ok(uploads);
    }
}
