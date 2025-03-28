package com.mindsync.lostandfound.lost_and_found_backend.service;

import com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Role;
import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
import com.mindsync.lostandfound.lost_and_found_backend.repository.ItemRepository;
import com.mindsync.lostandfound.lost_and_found_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    public ResponseEntity<Map<String,Object>> reportProduct(ItemDto itemDto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Retrieves email of logged-in seller

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));

        // Assign role OWNER if status is lost
        if ("lost".equalsIgnoreCase(String.valueOf(itemDto.getStatus()))) {
            if (!user.getRole().equals(Role.FINDER)) { // Prevent unnecessary updates
                user.setRole(Role.FINDER);
                userRepository.save(user); // Persist role change
            }
        }

        if(itemRepository.findByItemName(itemDto.getItemName()).isPresent())
        {
            Map<String,Object> response = new HashMap<>();
            response.put("message","item already added");
            response.put("item",itemDto);

            return ResponseEntity.ok(response);
        }


        Item item = Item.builder()
                .itemName(itemDto.getItemName())
                .itemDescription(itemDto.getItemDescription())
                .category(itemDto.getCategory())
                .status(itemDto.getStatus())
                .location(itemDto.getLocation())
                .date(itemDto.getDate())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        itemRepository.save(item);

        Map<String,Object> response = new HashMap<>();
        response.put("message","item added successfully");
        response.put("details",item);

        return ResponseEntity.ok(response);
    }
}
