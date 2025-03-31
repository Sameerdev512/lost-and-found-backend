package com.mindsync.lostandfound.lost_and_found_backend.service;

import com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.SecurityQuestionDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import com.mindsync.lostandfound.lost_and_found_backend.entity.SecurityQuestions;
import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
import com.mindsync.lostandfound.lost_and_found_backend.repository.ItemRepository;
import com.mindsync.lostandfound.lost_and_found_backend.repository.SecurityQuestionRepository;
import com.mindsync.lostandfound.lost_and_found_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SecurityQuestionService {

    SecurityQuestionRepository securityQuestionRepository;
    UserRepository userRepository;
    ItemRepository itemRepository;

    public ResponseEntity<Map<String,String>> addSecurityQuestions(List<SecurityQuestionDto> securityQuestionDto,Long itemId)
    {
        // Fetch existing security questions for the item
        List<SecurityQuestions> existingQuestions = securityQuestionRepository.findByItemId(itemId);


        // Check for duplicates
        for (SecurityQuestionDto dto : securityQuestionDto) {
            boolean isDuplicate = existingQuestions.stream()
                    .anyMatch(q -> q.getQuestion().equalsIgnoreCase(dto.getQuestion()) &&
                            q.getAnswer().equalsIgnoreCase(dto.getAnswer()));

            if (isDuplicate) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Duplicate question-answer pair already exists for this item");
                return ResponseEntity.badRequest().body(errorResponse);
            }
        }

        List<SecurityQuestions> securityQuestionsList = securityQuestionDto.stream()
                .map(dto -> SecurityQuestions.builder()
                        .question(dto.getQuestion())
                        .answer(dto.getAnswer())
                        .itemId(itemId)
                        .build())
                .collect(Collectors.toList());

        securityQuestionRepository.saveAll(securityQuestionsList);

        Map<String,String> response = new HashMap<>();
        response.put("message","questions added successfully");

        return ResponseEntity.ok(response);
    }

    public List<SecurityQuestions> findAllQuestions(Long itemId) {
        return securityQuestionRepository.findByItemId(itemId);
    }

    public ResponseEntity<Map<String, String>> validateSecurityAnswers(List<SecurityQuestionDto> answers,Long itemId) {

        //get details of the item for which we are validating security answers
        Item c_item = itemRepository.findByItemId(itemId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Retrieves email of logged-in seller

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));

        //taking all the items listed by users in all categories and with found or lost status
        List<ItemDto> userItems = itemRepository.findItemsByUserId(user.getId());

        //message if the users has not listed ant items
        if(userItems.isEmpty())
        {
            return createSuccessResponse("you have not listed any item.");
        }

        // extract all the items listed by the user with the same category of the item he is claiming the product
        List<ItemDto> matchedCategoryItems = getItemsByCategory(userItems, c_item.getCategory());

        //message if user haven't listed the item in the same category for that he is claiming for
        if (matchedCategoryItems.isEmpty()) {
            return createSuccessResponse("Item not found in your listed items. Please list the item first.in same category");
        }

        // Find the items that having status "lost" and pre-filtered by same category
        List<ItemDto> lostStatusItems = getItemsByLostStatus(matchedCategoryItems,"lost");

        //message if user not listed item with lost report type
        if (lostStatusItems.isEmpty()) {
            return createSuccessResponse("Item not found in your listed items. Please list the item first with lost report type");
        }

        //id the user id assosiated with the c_item and current logged-in in user id is same
        //means the finder is trying to claim the same product
        if(user.getId() == c_item.getUser().getId())
        {
            return createSuccessResponse("finder can not claim the same item");
        }


        for (SecurityQuestionDto dto : answers) {
            Optional<SecurityQuestions> storedQuestion = securityQuestionRepository.findById(dto.getId());

            if (storedQuestion.isEmpty()) {
                return createErrorResponse("Question ID not found: " + dto.getId());
            }

            if (!storedQuestion.get().getAnswer().equalsIgnoreCase(dto.getAnswer())) {
                return createSuccessResponse("Incorrect answer for questions ");
            }
        }
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        Item item = optionalItem.get();
        item.setStatus("claimed"); // Update status
        item.setClaimedUserId(user.getId());//set user id that claimed the product
        item.setClaimedAt(LocalDateTime.now());//set claiming time
        item.setClaimedUserName(user.getName());
        itemRepository.save(item);  // Save updated item
        return createSuccessResponse("All answers are correct");
    }

    //to take the all items of users that having the same category of product which he is claiming
    public List<ItemDto> getItemsByCategory(List<ItemDto> userItems, String category) {
        return userItems.stream()
                .filter(item -> item.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<ItemDto> getItemsByLostStatus(List<ItemDto> userItems, String reportType) {
        return userItems.stream()
                .filter(item -> item.getReportType().equals(reportType))
                .collect(Collectors.toList());
    }

    private ResponseEntity<Map<String, String>> createErrorResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "failed");
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
    }

    private ResponseEntity<Map<String, String>> createSuccessResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    public List<SecurityQuestions> getItemSecurityQuestions(Long itemId) {
        return securityQuestionRepository.findAllByItemId(itemId);
    }
}
