package com.mindsync.lostandfound.lost_and_found_backend.controller;

import com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpResponseDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpValidationRequestDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import com.mindsync.lostandfound.lost_and_found_backend.entity.SecurityQuestions;
import com.mindsync.lostandfound.lost_and_found_backend.service.ItemService;
import com.mindsync.lostandfound.lost_and_found_backend.service.OtpService;
import com.mindsync.lostandfound.lost_and_found_backend.service.SecurityQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finder")
@RequiredArgsConstructor
public class FinderController {
    public OtpService otpService;
    final ItemService itemService;
    private final SecurityQuestionService securityQuestionService;

    @PostMapping("/validate-otp/{itemId}")
    public ResponseEntity<OtpResponseDto> validateOtp(@RequestBody OtpValidationRequestDto otpValidationRequestDto, @PathVariable Long itemId) {
        System.out.println("in validate otp");
        OtpResponseDto validationResponse = otpService.validateOtp(itemId, otpValidationRequestDto);
        return ResponseEntity.ok(validationResponse);
    }

    @DeleteMapping("/delete-item/{id}")
    public String deleteItem(@PathVariable Long id)
    {
        return itemService.deleteItem(id);
    }

    @GetMapping("/get-all-items")
    public List<ItemDto> getAllItems()
    {
        return itemService.getAllItems();
    }

    @GetMapping("/get-all-claimed-items")
    public List<ItemDto> getAllClaimedItems()
    {
        return itemService.getAllClaimedItems();
    }

    //get all security questions related to the itemId
    @GetMapping("/get-item-security-questions/{itemId}")
    public List<SecurityQuestions> getItemsSecurityQuestions(@PathVariable Long itemId)
    {
        return securityQuestionService.getItemSecurityQuestions(itemId);
    }
}
