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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        // Assign role FINDER if status is lost
        if ("lost".equalsIgnoreCase(String.valueOf(itemDto.getStatus()))) {
            if (user.getRole().equals(Role.FINDER) ||user.getRole().equals(Role.OWNER) ) { // Prevent unnecessary updates
                user.setRole(Role.BOTH);
                userRepository.save(user); // Persist role change
            }else{
                user.setRole(Role.FINDER);
                userRepository.save(user); // Persist role change
            }
        }

        // Assign role OWNER if status is lost
        if ("found".equalsIgnoreCase(String.valueOf(itemDto.getStatus()))) {
            if (user.getRole().equals(Role.FINDER) ||user.getRole().equals(Role.OWNER) ) { // Prevent unnecessary updates
                user.setRole(Role.BOTH);
                userRepository.save(user); // Persist role change
            }else{
                user.setRole(Role.OWNER);
                userRepository.save(user); // Persist role change
            }
        }

        if (itemRepository.findByItemNameAndStatus(itemDto.getItemName(), itemDto.getStatus()).isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item with the same name and status already exists");
            response.put("item", itemDto);

            return ResponseEntity.ok(response);
        }




        Item item = Item.builder()
                .itemName(itemDto.getItemName())
                .itemDescription(itemDto.getItemDescription())
                .category(itemDto.getCategory())
                .status("Pending")
                .location(itemDto.getLocation())
                .date(itemDto.getDate())
                .createdAt(LocalDateTime.now())
                .finderOrOwnerName(user.getName())
                .user(user)
                .reportType(itemDto.getReportType())
                .build();

        itemRepository.save(item);

        Map<String,Object> response = new HashMap<>();
        response.put("message","item added successfully");
        response.put("details",item);

        return ResponseEntity.ok(response);
    }

    public List<ItemDto> getAllItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Retrieves email of logged-in seller

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return itemRepository.findItemsByUserId(user.getId());
    }

    public List<Item> getAllTheItems() {
        return itemRepository.findAll(); // Fetch all items from the database
    }

    public String deleteItem(Long id) {
        itemRepository.deleteById(id);
        return "deleted successfully";
    }

    public List<ItemDto> getAllClaimedItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Retrieves email of logged-in seller

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return itemRepository.findByClaimedUserId(user.getId());
    }
    // Utility method to convert entity to DTO

}
