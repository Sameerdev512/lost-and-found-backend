package com.mindsync.lostandfound.lost_and_found_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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
    private int itemId;

    private String itemName;
    private String itemDescription;

    private String status;

    private String category;

    private String location;
    private String date;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id") // This should match the query
    @JsonBackReference
    private User user;
}
