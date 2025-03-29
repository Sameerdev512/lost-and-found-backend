package com.mindsync.lostandfound.lost_and_found_backend.controller;

import com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.SecurityQuestionDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import com.mindsync.lostandfound.lost_and_found_backend.entity.SecurityQuestions;
import com.mindsync.lostandfound.lost_and_found_backend.service.ItemService;
import com.mindsync.lostandfound.lost_and_found_backend.service.SecurityQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:5173")
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
    public ResponseEntity<Map<String,Object>> reportProduct(@RequestBody ItemDto itemDto)
    {
        return itemService.reportProduct(itemDto);
    }

    @GetMapping("/get-all-items")
    public List<ItemDto> getAllItems()
    {
        return itemService.getAllItems();
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

    @DeleteMapping("/delete-item/{id}")
    public String deleteItem(@PathVariable Long id)
    {
        return itemService.deleteItem(id);
    }
}
