package com.example.Real_estate.seller.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String phone;

    private String profileImage;

    @Column(nullable = false)
    private Boolean isActive = true;

    private String userType = "SELLER";

    @Column(nullable = false)
    private String sellerType;

    // company fields
    private String companyName;

    private String businessRegistrationNumber;

    private String businessAddress;

    private String website;

    // individual fields
    private String occupation;

    private String nationality;

@Column(nullable = true)
private LocalDate dateOfBirth;

    // Common fields
    private Boolean isVerified = false;

    private Integer totalPropertieSold = 0;

    private Integer totalPropertiesListed;

    private Double rating = 0.0;

    private LocalDate registrationDate;

    private String preferredContactMethod;

    private String idProof;

    private String addressProof;

    private String businessLicense;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
