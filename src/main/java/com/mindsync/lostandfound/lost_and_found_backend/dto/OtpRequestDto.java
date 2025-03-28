package com.mindsync.lostandfound.lost_and_found_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequestDto {
    private String email; // Email to send OTP
    private Long itemId;  // Item ID retrieved from QR (if needed)
}
