package com.mindsync.lostandfound.lost_and_found_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String itemName;
    private String itemDescription;

    private String status;

    private String category;

    private String location;
    private LocalDateTime date;

    private LocalDateTime createdAt;

    private String reportType;

    private Long claimedUserId;
    private String finderOrOwnerName;

    private String claimedUserName;
    private LocalDateTime claimedAt;
    String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id") // This should match the query
    @JsonBackReference
    private User user;
}
