package com.mindsync.lostandfound.lost_and_found_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;  // Assuming there's a User entity

    private String firstName;

    private String lastName;

    private String phone;

    private String address;

    private String email;

    private String city;

    private String state;

    private String Department;
}
