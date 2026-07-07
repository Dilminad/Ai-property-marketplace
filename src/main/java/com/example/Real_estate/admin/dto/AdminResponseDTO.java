package com.example.Real_estate.admin.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String phone;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
