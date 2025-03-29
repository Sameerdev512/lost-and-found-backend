package com.mindsync.lostandfound.lost_and_found_backend.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "security_questions")
public class SecurityQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private String answer; // Finder's provided answer

    private Long itemId;

}
