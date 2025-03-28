package com.mindsync.lostandfound.lost_and_found_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
@CrossOrigin(origins="http://localhost:5173")
public class SellerController {

    @GetMapping("/dashboard")
    public String getSellerDashboard() {
        return "Seller dashboard data";
    }
}
