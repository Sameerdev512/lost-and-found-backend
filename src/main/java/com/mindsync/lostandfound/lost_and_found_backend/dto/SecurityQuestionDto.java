package com.mindsync.lostandfound.lost_and_found_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityQuestionDto {
    private Long id;

    @NotBlank(message="required")
    private String question;
    @NotBlank(message="required")
    private String answer; // Finder's provided answer
}
