package com.mindsync.lostandfound.lost_and_found_backend.repository;

import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Optional<Item> findByItemName(String itemName);



}
