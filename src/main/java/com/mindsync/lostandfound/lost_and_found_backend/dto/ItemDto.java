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
}
