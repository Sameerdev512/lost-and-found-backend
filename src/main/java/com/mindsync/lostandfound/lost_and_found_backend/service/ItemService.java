package com.mindsync.lostandfound.lost_and_found_backend.service;

import com.cloudinary.Cloudinary;
import com.mindsync.lostandfound.lost_and_found_backend.dto.ItemDto;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Item;
import com.mindsync.lostandfound.lost_and_found_backend.entity.Role;
import com.mindsync.lostandfound.lost_and_found_backend.entity.User;
import com.mindsync.lostandfound.lost_and_found_backend.repository.ItemRepository;
import com.mindsync.lostandfound.lost_and_found_backend.repository.UserRepository;
import com.mindsync.lostandfound.lost_and_found_backend.utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    private final CloudinaryService cloudinaryService;

    public ResponseEntity<Map<String,Object>> reportProduct(String name,String description,String status,String category,String reportType,String location,MultipartFile imageUrl,String date) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Retrieves email of logged-in seller

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));

        // Assign role FINDER if status is lost
        if ("lost".equalsIgnoreCase(String.valueOf(status))) {
            if (user.getRole().equals(Role.FINDER) ||user.getRole().equals(Role.OWNER) ) { // Prevent unnecessary updates
                user.setRole(Role.BOTH);
                userRepository.save(user); // Persist role change
            }else{
                user.setRole(Role.FINDER);
                userRepository.save(user); // Persist role change
            }
        }

        // Assign role OWNER if status is lost
        if ("found".equalsIgnoreCase(String.valueOf(status))) {
            if (user.getRole().equals(Role.FINDER) ||user.getRole().equals(Role.OWNER) ) { // Prevent unnecessary updates
                user.setRole(Role.BOTH);
                userRepository.save(user); // Persist role change
            }else{
                user.setRole(Role.OWNER);
                userRepository.save(user); // Persist role change
            }
        }

        if (itemRepository.findByItemNameAndStatus(name, status).isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item with the same name and status already exists");
            response.put("item", name);

            return ResponseEntity.ok(response);
        }


        String imageString = cloudinaryService.uploadFile(imageUrl);

        Item item = Item.builder()
                .itemName(name)
                .itemDescription(description)
                .category(category)
                .status("Pending")
                .location(location)
                .date(LocalDateTime.parse(date))
                .createdAt(LocalDateTime.now())
                .finderOrOwnerName(user.getName())
                .user(user)
                .reportType(reportType)
                .imageUrl(imageString)
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
