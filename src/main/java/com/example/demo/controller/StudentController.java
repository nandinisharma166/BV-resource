package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")  // for frontend access
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/submit")
    public String addStudent(@RequestBody Student student) {
        if (studentRepository.existsById(student.getCollegeId())) {
            return "Student already exists. No action taken.";
        } else {
            studentRepository.save(student);
            return "Student saved successfully.";
        }
    }
}
