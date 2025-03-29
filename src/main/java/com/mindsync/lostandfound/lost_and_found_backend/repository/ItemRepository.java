package com.mindsync.lostandfound.lost_and_found_backend.repository;

import com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Optional<Item> findByItemName(String itemName);
    Optional<Item> findByItemNameAndStatus(String itemName, String status);

    @Query("SELECT new com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto(i.id, i.itemName, i.itemDescription, i.status, i.category, i.location, i.date, i.createdAt,i.reportType) FROM Item i WHERE i.user.id = :userId")
    List<ItemDto> findItemsByUserId(@Param("userId") Long userId);
}
