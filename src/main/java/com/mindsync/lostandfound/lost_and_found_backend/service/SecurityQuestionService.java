package com.mindsync.lostandfound.lost_and_found_backend.service;

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
        for (SecurityQuestionDto dto : answers) {
            Optional<SecurityQuestions> storedQuestion = securityQuestionRepository.findById(dto.getId());

            if (storedQuestion.isEmpty()) {
                return createErrorResponse("Question ID not found: " + dto.getId());
            }

            if (!storedQuestion.get().getAnswer().equalsIgnoreCase(dto.getAnswer())) {
                return createErrorResponse("Incorrect answer for question: " + storedQuestion.get().getQuestion());
            }
        }
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        Item item = optionalItem.get();
        item.setStatus("claimed"); // Update status
        itemRepository.save(item);  // Save updated item
        return createSuccessResponse("All answers are correct");
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
}
