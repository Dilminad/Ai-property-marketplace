package com.example.Real_estate.seller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String phone;
    private String profileImage;
    private String userType;
    private Boolean isActive;
    private String sellerType;
    private String companyName;
    private String businessRegistrationNumber;
    private String taxId;
    private String businessAddress;
    private String website;
    private String occupation;
    private String nationality;
    private LocalDate dateOfBirth;
    private Boolean isVerified;
    private Integer totalPropertiesSold;
    private Integer totalPropertiesListed;
    private Double rating;
    private LocalDate registrationDate;
    private String preferredContactMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
