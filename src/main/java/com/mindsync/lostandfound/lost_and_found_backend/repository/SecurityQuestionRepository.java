package com.mindsync.lostandfound.lost_and_found_backend.repository;

import com.mindsync.lostandfound.lost_and_found_backend.entity.SecurityQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestions, Long> {
    List<SecurityQuestions> findByItemId(Long itemId);
}
