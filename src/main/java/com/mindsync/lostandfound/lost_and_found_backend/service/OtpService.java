package com.mindsync.lostandfound.lost_and_found_backend.service;

import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpRequestDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpResponseDto;
import com.mindsync.lostandfound.lost_and_found_backend.dto.OtpValidationRequestDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Otp;
import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
import com.mindsync.lostandfound.lost_and_found_backend.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

    @Value("${otp.expiration.minutes:5}") // Default OTP expiration time is 5 minutes
    private int otpExpirationMinutes;

    /**
     * ✅ Sends an OTP to the registered user's email.
     */
    public OtpResponseDto sendOtp(OtpRequestDto otpRequestDto,Long itemId) {
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
                    .itemId(itemId)
                    .email(otpRequestDto.getEmail())
                    .generatedAt(LocalDateTime.now())
                    .expirationTime(LocalDateTime.now().plusHours(24))
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
    public OtpResponseDto validateOtp(Long itemId, OtpValidationRequestDto otpValidationRequestDto) {
        System.out.println("in otp service");
        try {
            // Fetch the most recent unused OTP for the given item
            Optional<Otp> optionalOtp = otpRepository.findTopByItemIdAndOtpCodeAndIsUsedFalseOrderByGeneratedAtDesc(
                    itemId, otpValidationRequestDto.getOtpCode()
            );

            if (optionalOtp.isEmpty()) {
                return OtpResponseDto.builder()
                        .statusCode(400)
                        .responseMessage("Invalid or expired OTP for this item.")
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

            // Mark OTP as used
            otp.setUsed(true);
            otpRepository.save(otp);

            // Update item status to 'Returned'
            Optional<Item> optionalItem = itemRepository.findById(itemId);
            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                item.setStatus("Returned");
                itemRepository.save(item);
            }

            return OtpResponseDto.builder()
                    .statusCode(200)
                    .responseMessage("OTP validated successfully. Item marked as returned.")
                    .isOtpValid(true)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while validating OTP for item {}: {}", itemId, e.getMessage(), e);
            return OtpResponseDto.builder()
                    .statusCode(500)
                    .responseMessage("Failed to validate OTP.")
                    .isOtpValid(false)
                    .build();
        }
    }

}
