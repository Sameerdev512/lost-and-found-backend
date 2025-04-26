package com.mindsync.lostandfound.lost_and_found_backend.controller;

import com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.SecurityQuestionDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import com.mindsync.lostandfound.lost_and_found_backend.entity.SecurityQuestions;
import com.mindsync.lostandfound.lost_and_found_backend.service.ItemService;
import com.mindsync.lostandfound.lost_and_found_backend.service.SecurityQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    final private ItemService itemService;

    final private SecurityQuestionService securityQuestionService;

    public UserController(ItemService itemService, SecurityQuestionService securityQuestionService) {
        this.itemService = itemService;
        this.securityQuestionService = securityQuestionService;
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getUserProfile() {
        System.out.println("🚀 User profile endpoint hit!");
        return ResponseEntity.ok("User profile data retrieved successfully!");
    }

    @PostMapping("/report-product")
    public ResponseEntity<Map<String,Object>> reportProduct(@RequestParam("name") String name,
                                                            @RequestParam("description") String description,
                                                            @RequestParam("status") String status,
                                                            @RequestParam("category") String category,
                                                            @RequestParam("reportType") String reportType,
                                                            @RequestParam("location") String location,
                                                            @RequestParam("date") String date,
                                                            @RequestParam("imageUrl") MultipartFile imageUrl) throws IOException {
        return itemService.reportProduct(name,description,status,category,reportType,location,imageUrl,date);
    }



    @GetMapping("/get-all-questions/{itemId}")
    public List<SecurityQuestions> getAllQuestions(@PathVariable Long itemId) {
        return securityQuestionService.findAllQuestions(itemId);
    }

    @PostMapping("security-questions/validate/{itemId}")
    public ResponseEntity<Map<String, String>> validateSecurityAnswers(@RequestBody List<SecurityQuestionDto> answers,@PathVariable Long itemId) {
        return securityQuestionService.validateSecurityAnswers(answers,itemId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllTheItems() {
        List<Item> items = itemService.getAllTheItems();
        return ResponseEntity.ok(items);
    }


}
