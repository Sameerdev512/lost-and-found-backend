package com.mindsync.lostandfound.lost_and_found_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_details")  // Use snake_case for consistency in DB naming
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDetailsId;  // Primary Key

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;  // Assuming there's a User entity

    private String fullName;

    private String phone;

    private String address;

    private String city;

    private String state;
}
