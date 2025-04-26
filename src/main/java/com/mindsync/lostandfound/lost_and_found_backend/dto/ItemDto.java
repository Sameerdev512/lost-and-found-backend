package com.mindsync.lostandfound.lost_and_found_backend.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String itemName;
    private String itemDescription;
    private String status;
    private String category;
    private String location;
    private LocalDateTime date;
    private LocalDateTime createdAt;
    private String reportType;
    private Long claimedUserId;
    private String finderOrOwnerName;
    private String claimedUserName;
    private LocalDateTime claimedAt;
    private Long userId; // New field for the item owner's ID
    private String imageUrl;

    // Updated constructor
    public ItemDto(Long id, String itemName, String itemDescription, String status,
                   String category, String location, LocalDateTime date,
                   LocalDateTime createdAt, String reportType, Long claimedUserId,
                   String finderOrOwnerName, String claimedUserName,
                   LocalDateTime claimedAt, Long userId,String imageUrl) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.status = status;
        this.category = category;
        this.location = location;
        this.date = date;
        this.createdAt = createdAt;
        this.reportType = reportType;
        this.claimedUserId = claimedUserId;
        this.finderOrOwnerName = finderOrOwnerName;
        this.claimedUserName = claimedUserName;
        this.claimedAt = claimedAt;
        this.userId = userId; // Initialize the new field
        this.imageUrl = imageUrl;
    }
}