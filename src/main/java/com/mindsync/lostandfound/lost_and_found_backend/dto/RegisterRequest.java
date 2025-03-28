package com.mindsync.lostandfound.lost_and_found_backend.dto;

import com.mindsync.lostandfound.lost_and_found_backend.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message="required")
    @Email(message="email is required")
    private String username;

    @NotBlank(message="required")
    @Size(min = 8,message="password should of 8 character")
    private String password;

    @NotBlank(message="required")
    private Role role;

    @NotBlank(message="required")
    private String name;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
