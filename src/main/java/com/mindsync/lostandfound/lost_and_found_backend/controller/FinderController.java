package com.mindsync.lostandfound.lost_and_found_backend.controller;

import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpResponseDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpValidationRequestDto;
import com.mindsync.lostandfound.lost_and_found_backend.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins="http://localhost:5173")
public class FinderController {
    public OtpService otpService;

    @PostMapping("/validate-otp/{itemId}")
    public ResponseEntity<OtpResponseDto> validateOtp(@RequestBody OtpValidationRequestDto otpValidationRequestDto, @PathVariable Long itemId) {
        System.out.println("in validate otp");
        OtpResponseDto validationResponse = otpService.validateOtp(itemId, otpValidationRequestDto);
        return ResponseEntity.ok(validationResponse);
    }
}
