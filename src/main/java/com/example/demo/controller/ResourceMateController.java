package com.example.demo.controller;

import com.example.demo.entity.ResourceMate;
import com.example.demo.repository.ResourceMateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mates")
@CrossOrigin(origins = "*")
public class ResourceMateController {

    @Autowired
    private ResourceMateRepository mateRepo;

    // ✅ Sign up
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody ResourceMate mate) {
        if (mateRepo.findByEmail(mate.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        mateRepo.save(mate);
        return ResponseEntity.ok("Signup successful");
    }

    // ✅ Login (optional for now)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ResourceMate mate) {
        ResourceMate existing = mateRepo.findByEmail(mate.getEmail());
        if (existing != null && existing.getPassword().equals(mate.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
