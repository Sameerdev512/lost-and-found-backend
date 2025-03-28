package com.mindsync.lostandfound.lost_and_found_backend.service;

import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpRequestDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpResponseDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpValidationRequestDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Otp;
import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
import com.mindsync.lostandfound.lost_and_found_backend.repository.OtpRepository;
import com.mindsync.lostandfound.lost_and_found_backend.repository.UserRepository;
import com.mindsync.lostandfound.lost_and_found_backend.utils.OtpGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Value("${otp.expiration.minutes:5}") // Default OTP expiration time is 5 minutes
    private int otpExpirationMinutes;

    /**
     * ✅ Sends an OTP to the registered user's email.
     */
    public OtpResponseDto sendOtp(OtpRequestDto otpRequestDto) {
        try {
            // Check if user exists with the given email
            User user = userRepository.findByUsername(otpRequestDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + otpRequestDto.getEmail()));

            // Generate new OTP
            String otp = OtpGenerator.generateOtp();
            log.info("Generated OTP for {}: {}", user.getUsername(), otp);

            // Save OTP in database
            Otp newOtp = Otp.builder()
                    .user(user) // Associate OTP with the User entity
                    .otpCode(otp)
                    .generatedAt(LocalDateTime.now())
                    .expirationTime(LocalDateTime.now().plusMinutes(otpExpirationMinutes))
                    .isUsed(false)
                    .build();

            otpRepository.save(newOtp);

            // Send OTP via email
            String emailBody = "Your OTP is: " + otp + ". It is valid for " + otpExpirationMinutes + " minutes.";
            emailService.sendEmail(user.getUsername(), "Lost & Found - OTP Verification", emailBody);

            return OtpResponseDto.builder()
                    .statusCode(200)
                    .responseMessage("OTP sent successfully.")
                    .isOtpValid(false) // Initially false, user must validate it
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while sending OTP for {}: {}", otpRequestDto.getEmail(), e.getMessage(), e);
            return OtpResponseDto.builder()
                    .statusCode(500)
                    .responseMessage("Failed to send OTP.")
                    .isOtpValid(false)
                    .build();
        }
    }

    /**
     * ✅ Validates the OTP entered by the user.
     */
    public OtpResponseDto validateOtp(OtpValidationRequestDto otpValidationRequestDto) {
        try {
            // Find user by email
            User user = userRepository.findByUsername(otpValidationRequestDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + otpValidationRequestDto.getEmail()));

            // Fetch the most recent unused OTP for the user
            Optional<Otp> optionalOtp = otpRepository.findTopByUserAndIsUsedFalseOrderByGeneratedAtDesc(user);

            if (optionalOtp.isEmpty()) {
                return OtpResponseDto.builder()
                        .statusCode(400)
                        .responseMessage("No OTP found for the given email.")
                        .isOtpValid(false)
                        .build();
            }

            Otp otp = optionalOtp.get();

            // Check if OTP is expired
            if (otp.getExpirationTime().isBefore(LocalDateTime.now())) {
                otpRepository.delete(otp); // Remove expired OTP
                return OtpResponseDto.builder()
                        .statusCode(400)
                        .responseMessage("Expired OTP.")
                        .isOtpValid(false)
                        .build();
            }

            // Validate OTP (using Objects.equals to prevent NullPointerException)
            if (!Objects.equals(otpValidationRequestDto.getOtpCode(), otp.getOtpCode())) {
                return OtpResponseDto.builder()
                        .statusCode(400)
                        .responseMessage("Invalid OTP.")
                        .isOtpValid(false)
                        .build();
            }

            // Mark OTP as used
            otp.setUsed(true);
            otpRepository.save(otp);

            return OtpResponseDto.builder()
                    .statusCode(200)
                    .responseMessage("OTP validated successfully.")
                    .isOtpValid(true)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while validating OTP for {}: {}", otpValidationRequestDto.getEmail(), e.getMessage(), e);
            return OtpResponseDto.builder()
                    .statusCode(500)
                    .responseMessage("Failed to validate OTP.")
                    .isOtpValid(false)
                    .build();
        }
    }
}
