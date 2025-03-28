package com.mindsync.lostandfound.lost_and_found_backend.repository;

import com.mindsync.lostandfound.lost_and_found_backend.entity.Otp;
import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findTopByEmailAndIsUsedFalseOrderByGeneratedAtDesc(String email);

    Optional<Otp> findTopByUserAndIsUsedFalseOrderByGeneratedAtDesc(User user);
}
