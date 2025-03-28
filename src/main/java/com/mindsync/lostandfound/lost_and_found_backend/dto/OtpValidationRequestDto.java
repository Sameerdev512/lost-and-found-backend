package com.mindsync.lostandfound.lost_and_found_backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpValidationRequestDto {
    private String email;   // User's email to validate OTP
    private String otpCode; // OTP code sent via email
}
