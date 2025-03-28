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
        //this all will be in register item function
        //fist item will save then it will be mapped with the security question

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName(); // Retrieves email of logged-in seller

//        User user = userRepository.findByUsername(userEmail)
//                .orElseThrow(() -> new RuntimeException("user not found"));

//        // Fetch the Item by itemId
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemId));

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
