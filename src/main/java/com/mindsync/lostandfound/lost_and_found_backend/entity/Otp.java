package com.mindsync.lostandfound.lost_and_found_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user") // Avoid circular reference in toString
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long otpId; // Primary Key (Auto-Increment)

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for optimization
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;  // User who is logging in


    private Long itemId;  // Sent from frontend by scanning QR


    private Long finderId;  // Finder ID retrieved from item table

    private String email;
    private String otpCode;  // OTP Code

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;  // OTP Generation Time

    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;  // Expiry Time

    @Column(name = "is_used", nullable = false)
    private boolean isUsed = false;  // Initially false

    // Ensure expiration time is set when generatedAt is set
    @PrePersist
    public void prePersist() {
        this.expirationTime = this.generatedAt.plusDays(1);
    }
}
