package com.mindsync.lostandfound.lost_and_found_backend.repository;

import com.mindsync.lostandfound.lost_and_found_backend.dto.SecurityQuestionDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import com.mindsync.lostandfound.lost_and_found_backend.entity.SecurityQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestions, Long> {
    List<SecurityQuestions> findByItemId(Long itemId);


    List<SecurityQuestions> findAllByItemId(Long itemId);
}
