package com.mindsync.lostandfound.lost_and_found_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpResponseDto {
    private int statusCode;        // Status code (e.g., 200 for success, 400 for failure)
    private String responseMessage; // Message indicating success or failure
    private boolean isOtpValid;     // Whether OTP is valid or not
}
