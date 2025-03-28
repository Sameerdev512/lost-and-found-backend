package com.mindsync.lostandfound.lost_and_found_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:5173")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<String> getUserProfile() {
        System.out.println("🚀 User profile endpoint hit!");
        return ResponseEntity.ok("User profile data retrieved successfully!");
    }
}
