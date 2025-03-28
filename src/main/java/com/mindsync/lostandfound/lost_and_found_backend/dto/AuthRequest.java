package com.mindsync.lostandfound.lost_and_found_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @NotBlank(message="required")
    @Email(message="email is required")
    private String username;

    @Size(min = 8,message="password should of 8 character")
    @NotBlank(message="required")
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
