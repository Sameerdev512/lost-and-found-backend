package com.mindsync.lostandfound.lost_and_found_backend.controller;

import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpRequestDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpResponseDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpValidationRequestDto;
import com.mindsync.lostandfound.lost_and_found_backend.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    @GetMapping("/checkOtp")
    public ResponseEntity<String> checkOtpService() {
        return ResponseEntity.ok("OTP Service is running.");
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<OtpResponseDto> sendOtp(@RequestBody OtpRequestDto otpRequestDto) {
        OtpResponseDto response = otpService.sendOtp(otpRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<OtpResponseDto> validateOtp(@RequestBody OtpValidationRequestDto otpValidationRequestDto) {
        OtpResponseDto validationResponse = otpService.validateOtp(otpValidationRequestDto);
        return ResponseEntity.ok(validationResponse);
    }
}
