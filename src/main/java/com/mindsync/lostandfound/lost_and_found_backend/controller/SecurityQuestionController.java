package com.mindsync.lostandfound.lost_and_found_backend.controller;

import com.mindsync.lostandfound.lost_and_found_backend.dto.SecurityQuestionDto;
import com.mindsync.lostandfound.lost_and_found_backend.service.SecurityQuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/finder/")
public class SecurityQuestionController {

    SecurityQuestionService securityQuestionService;

    @PostMapping("/security-questions/{itemId}")
    public ResponseEntity<Map<String, String>> addSecurityQuestions(
            @Valid @RequestBody List<SecurityQuestionDto> securityQuestionDto,
            @PathVariable Long itemId) {

        return securityQuestionService.addSecurityQuestions(securityQuestionDto, itemId);
    }

}
