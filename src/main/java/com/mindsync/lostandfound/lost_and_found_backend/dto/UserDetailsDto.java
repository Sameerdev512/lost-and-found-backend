package com.mindsync.lostandfound.lost_and_found_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString
public class UserDetailsDto {
    private Long userDetailsId;

    @NotBlank(message = "Full name cannot be empty")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private String address;

    private String city;

    private String email;

    private String state;

    private String department;
}
