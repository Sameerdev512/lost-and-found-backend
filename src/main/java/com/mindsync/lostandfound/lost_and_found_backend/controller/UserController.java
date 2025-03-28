package com.mindsync.lostandfound.lost_and_found_backend.controller;

import com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto;
import com.mindsync.lostandfound.lost_and_found_backend.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:5173")
public class UserController {

    final private ItemService itemService;

    public UserController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getUserProfile() {
        System.out.println("🚀 User profile endpoint hit!");
        return ResponseEntity.ok("User profile data retrieved successfully!");
    }

    @PostMapping("/report-product")
    public ResponseEntity<Map<String,String>> reportProduct(@RequestBody ItemDto itemDto)
    {
        return itemService.reportProduct(itemDto);
    }
}
