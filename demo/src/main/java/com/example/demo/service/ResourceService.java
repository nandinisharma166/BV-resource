//package com.example.demo.service;
//import com.example.demo.entity.Resource;
//import com.example.demo.repository.ResourceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@Service
//public class ResourceService {
//
//    @Autowired
//    private ResourceRepository resourceRepository;
//
//    // ---------- 1. Upload the PDF file ----------
//    public String uploadResource(MultipartFile file, String name, String subject, String semester, String year) {
//        try {
//            Resource resource = new Resource();
//            resource.setName(name);
//            resource.setSubject(subject);
//            resource.setSemester(semester);
//            resource.setYear(year);
//            resource.setFileData(file.getBytes());
//
//            Resource saved = resourceRepository.save(resource);
//            return saved.getId();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload file: " + e.getMessage());
//        }
//    }
//
//    // ---------- 2. Get PDF by ID ----------
//    public Optional<Resource> getResourceById(String id) {
//        return resourceRepository.findById(id);
//    }
//}
