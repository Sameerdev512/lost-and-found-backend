package com.mindsync.lostandfound.lost_and_found_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private int itemId;

    private String itemName;
    private String itemDescription;

    private String status;

    private String category;

    private String location;
    private String date;

    private LocalDateTime createdAt;

    private String reportType;

    public ItemDto(int itemId, String itemName, String itemDescription, String status, String category, String location, String date, LocalDateTime createdAt,String reportType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.status = status;
        this.category = category;
        this.location = location;
        this.date = date;
        this.createdAt = createdAt;
        this.reportType = reportType;
    }

}
