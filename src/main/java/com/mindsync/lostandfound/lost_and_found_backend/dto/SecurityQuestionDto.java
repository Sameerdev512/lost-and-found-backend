package com.mindsync.lostandfound.lost_and_found_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityQuestionDto {
    private Long id;
    private String question;
    private String answer;

    public SecurityQuestionDto(String question, String answer) {
        this.answer = answer;
        this.question =question;
    }
}
