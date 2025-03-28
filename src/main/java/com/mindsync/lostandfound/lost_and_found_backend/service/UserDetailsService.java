//package com.mindsync.lostandfound.lost_and_found_backend.service;
//
//
//import com.mindsync.lostandfound.lost_and_found_backend.dto.UserDetailsDto;
//import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
//import com.mindsync.lostandfound.lost_and_found_backend.entity.UserDetails;
//import com.mindsync.lostandfound.lost_and_found_backend.repository.UserDetailsRepository;
//import com.mindsync.lostandfound.lost_and_found_backend.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailsService {
//
//    private final UserDetailsRepository userDetailsRepository;
//    private final UserRepository userRepository;
//
//
//    public ResponseEntity<Map<String,String>> updateUserDetails(UserDetailsDto userDetailsDto){
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName(); // Retrieves email of logged-in seller
//
//        User user = userRepository.findByUsername(userEmail)
//                .orElseThrow(() -> new RuntimeException("user not found"));
//
//
//        UserDetails userDetails = UserDetails.builder()
//                .user(user)
//                .fullName(userDetailsDto.getFullName())
//                .phone(userDetailsDto.getPhone())
//                .address(userDetailsDto.getAddress())
//                .city(userDetailsDto.getCity())
//                .state(userDetailsDto.getState())
//                .build();
//
//        // Save entity
//        UserDetails savedUserDetails = userDetailsRepository.save(userDetails);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("status", "okay");
//        response.put("message", "user details updated successfully.");
//
//        return ResponseEntity.ok(response);
//
//
//
//
//    }
//
//
//
//
//
//
//
//
//}

package com.mindsync.lostandfound.lost_and_found_backend.service;

import com.mindsync.lostandfound.lost_and_found_backend.dto.UserDetailsDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
import com.mindsync.lostandfound.lost_and_found_backend.entity.UserDetails;
import com.mindsync.lostandfound.lost_and_found_backend.repository.UserDetailsRepository;
import com.mindsync.lostandfound.lost_and_found_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Map<String, String>> updateUserDetails(UserDetailsDto userDetailsDto) {
        // Debugging: Print received DTO
        System.out.println("Received DTO: " + userDetailsDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Retrieves email of logged-in user

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if UserDetails already exists
        UserDetails userDetails = userDetailsRepository.findByUser(user)
                .orElse(new UserDetails()); // If not found, create a new one

        // Set user details
        userDetails.setUser(user);
        userDetails.setFullName(userDetailsDto.getFullName());
        userDetails.setPhone(userDetailsDto.getPhone());
        userDetails.setAddress(userDetailsDto.getAddress());
        userDetails.setCity(userDetailsDto.getCity());
        userDetails.setState(userDetailsDto.getState());

        // Debugging: Print entity before saving
        System.out.println("Saving UserDetails: " + userDetails);

        // Save or update entity
        userDetailsRepository.save(userDetails);

        // Prepare response
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "User details updated successfully.");

        return ResponseEntity.ok(response);
    }
}


