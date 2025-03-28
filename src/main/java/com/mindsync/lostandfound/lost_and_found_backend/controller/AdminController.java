package com.mindsync.lostandfound.lost_and_found_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins="http://localhost:5173")
public class AdminController {

    @GetMapping("/dashboard")
    public String getAdminDashboard() {
        return "Admin dashboard data";
    }
}
