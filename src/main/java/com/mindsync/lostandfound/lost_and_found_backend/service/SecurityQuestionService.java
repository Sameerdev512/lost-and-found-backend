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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
