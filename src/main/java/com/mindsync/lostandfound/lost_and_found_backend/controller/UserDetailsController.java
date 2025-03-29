package com.mindsync.lostandfound.lost_and_found_backend.controller;

import com.mindsync.lostandfound.lost_and_found_backend.dto.UserDetailsDto;
import com.mindsync.lostandfound.lost_and_found_backend.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:5173")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @PutMapping("/updateUserDetails") // Use PUT for updating existing records
    public ResponseEntity<Map<String, String>> updateUserDetails(@RequestBody UserDetailsDto userDetailsDto) {
        return userDetailsService.updateUserDetails(userDetailsDto);
    }
}
