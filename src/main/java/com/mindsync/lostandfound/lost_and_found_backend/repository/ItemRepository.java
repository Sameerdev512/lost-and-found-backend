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

    @Query("SELECT new com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto(" +
            "i.id, i.itemName, i.itemDescription, i.status, i.category, i.location, " +
            "i.date, i.createdAt, i.reportType, i.claimedUserId, i.finderOrOwnerName, " +
            "i.claimedUserName, i.claimedAt,i.user.id) " +  // ✅ Corrected order
            "FROM Item i WHERE i.user.id = :userId")
    List<ItemDto> findItemsByUserId(@Param("userId") Long userId);




    Item findByItemId(Long itemId);

    // Find all items where claimedUserId matches the provided ID
    @Query("SELECT new com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto(" +
            "i.id, i.itemName, i.itemDescription, i.status, i.category, i.location, " +
            "i.date, i.createdAt, i.reportType, i.claimedUserId, i.finderOrOwnerName, " +
            "i.claimedUserName, i.claimedAt,i.user.id) " +  // ✅ Corrected order
            "FROM Item i WHERE i.claimedUserId = :claimedUserId")
    List<ItemDto> findByClaimedUserId(@Param("claimedUserId") Long claimedUserId);
}
