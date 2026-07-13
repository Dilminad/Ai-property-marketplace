package com.example.Real_estate.seller.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Real_estate.seller.dto.SellerRequestDTO;
import com.example.Real_estate.seller.dto.SellerResponseDTO;
import com.example.Real_estate.seller.entity.Seller;
import com.example.Real_estate.seller.repository.SellerRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SellerResponseDTO createSeller(SellerRequestDTO requestDTO) {
        if (sellerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already Registered");
        }
        if (sellerRepository.existsByUserName(requestDTO.getUserName())) {
            throw new RuntimeException("Email already taken");
        }
        if (sellerRepository.existsByPhone(requestDTO.getPhone())) {
            throw new RuntimeException("Phonenumber already taken");
        }
        if (!"INDIVIDUAL".equals(requestDTO.getSellerType()) && !"COMPANY".equals(requestDTO.getSellerType())) {
            throw new RuntimeException("Seller type must be INDIVIDUAL or COMPANY");
        }

        Seller.SellerBuilder builder = Seller.builder()
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .userName(requestDTO.getUserName())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .phone(requestDTO.getPhone())
                .profileImage(requestDTO.getProfileImage())
                .isActive(true)
                .userType("SELLER")
                .sellerType(requestDTO.getSellerType())
                .isVerified(false)
                .totalPropertieSold(0)
                .totalPropertiesListed(0)
                .rating(0.0)
                .registrationDate(LocalDate.now())
                .preferredContactMethod(requestDTO.getPreferredContactMethod());

        if ("COMPANY".equals(requestDTO.getSellerType())) {
            builder.companyName(requestDTO.getCompanyName())
                    .businessRegistrationNumber(requestDTO.getBusinessRegistrationNumber())
    
                    .businessAddress(requestDTO.getBusinessAddress())
                 .website(requestDTO.getWebsite());

        }else {
            builder.occupation(requestDTO.getOccupation())
                    .nationality(requestDTO.getNationality())
                    .dateOfBirth(requestDTO.getDateOfBirth());

        }
           Seller savedSeller = sellerRepository.save(builder.build());
        return mapToResponseDTO(savedSeller);

        }
           public SellerResponseDTO getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        return mapToResponseDTO(seller);
    }
    private SellerResponseDTO mapToResponseDTO(Seller seller) {
    return SellerResponseDTO.builder()
            .id(seller.getId())
            .firstName(seller.getFirstName())
            .lastName(seller.getLastName())
            .email(seller.getEmail())
            .userName(seller.getUserName())
            .phone(seller.getPhone())
            .profileImage(seller.getProfileImage())
            .userType(seller.getUserType())
            .isActive(seller.getIsActive())
            .sellerType(seller.getSellerType())
            .companyName(seller.getCompanyName())
            .businessRegistrationNumber(seller.getBusinessRegistrationNumber())
            .businessAddress(seller.getBusinessAddress())
            .website(seller.getWebsite())
            .occupation(seller.getOccupation())
            .nationality(seller.getNationality())
            .dateOfBirth(seller.getDateOfBirth()) // Direct assignment, no parsing needed
            .isVerified(seller.getIsVerified())
            .totalPropertiesSold(seller.getTotalPropertieSold())
            .totalPropertiesListed(seller.getTotalPropertiesListed())
            .rating(seller.getRating())
            .registrationDate(seller.getRegistrationDate())
            .preferredContactMethod(seller.getPreferredContactMethod())
            .createdAt(seller.getCreatedAt())
            .updatedAt(seller.getUpdatedAt())
            .build();
}
    }

