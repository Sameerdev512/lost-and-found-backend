package com.mindsync.lostandfound.lost_and_found_backend.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    /**
     * Sends an email with the given recipient, subject, and message body.
     *
     * @param recipient The recipient's email address.
     * @param subject   The subject of the email.
     * @param message   The body of the email.
     */
    public void sendEmail(String recipient, String subject, String message) {
        try {
            // Create a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailSender);
            mailMessage.setTo(recipient);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            // Send the email
            javaMailSender.send(mailMessage);

            log.info("Email sent successfully to {}", recipient);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", recipient, e.getMessage(), e);
        }
    }
}
