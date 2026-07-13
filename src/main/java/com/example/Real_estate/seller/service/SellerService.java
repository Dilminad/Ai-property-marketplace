package com.example.Real_estate.seller.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    //  CREATE SELLER 
    @Transactional
    public SellerResponseDTO createSeller(SellerRequestDTO requestDTO) {
        if (sellerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already Registered");
        }
        if (sellerRepository.existsByUserName(requestDTO.getUserName())) {
            throw new RuntimeException("Username already taken");
        }
        if (sellerRepository.existsByPhone(requestDTO.getPhone())) {
            throw new RuntimeException("Phone number already taken");
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
        } else {
            builder.occupation(requestDTO.getOccupation())
                    .nationality(requestDTO.getNationality())
                    .dateOfBirth(requestDTO.getDateOfBirth());
        }

        Seller savedSeller = sellerRepository.save(builder.build());
        return mapToResponseDTO(savedSeller);
    }

    // GET SELLER BY ID
    public SellerResponseDTO getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));
        return mapToResponseDTO(seller);
    }

    // GET SELLER BY EMAIL
    public SellerResponseDTO getSellerByEmail(String email) {
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Seller not found with email: " + email));
        return mapToResponseDTO(seller);
    }

    // GET ALL SELLERS 
    public List<SellerResponseDTO> getAllSellers() {
        return sellerRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //  GET VERIFIED SELLERS 
    public List<SellerResponseDTO> getVerifiedSellers() {
        return sellerRepository.findByIsVerified(true).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //  GET INDIVIDUAL SELLERS 
    public List<SellerResponseDTO> getIndividualSellers() {
        return sellerRepository.findBySellerType("INDIVIDUAL").stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // GET COMPANY SELLERS 
    public List<SellerResponseDTO> getCompanySellers() {
        return sellerRepository.findBySellerType("COMPANY").stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //  GET TOP RATED SELLERS 
    public List<SellerResponseDTO> getTopRatedSellers() {
        return sellerRepository.findByRatingGreaterThanEqual(4.0).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //  UPDATE SELLER
    @Transactional
    public SellerResponseDTO updateSeller(Long id, SellerRequestDTO requestDTO) {
        Seller existingSeller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));

        // Check for duplicate email (if changed)
        if (!existingSeller.getEmail().equals(requestDTO.getEmail()) 
                && sellerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already registered to another seller");
        }

        // Check for duplicate username (if changed)
        if (!existingSeller.getUserName().equals(requestDTO.getUserName()) 
                && sellerRepository.existsByUserName(requestDTO.getUserName())) {
            throw new RuntimeException("Username already taken by another seller");
        }

        // Check for duplicate phone (if changed)
        if (!existingSeller.getPhone().equals(requestDTO.getPhone()) 
                && sellerRepository.existsByPhone(requestDTO.getPhone())) {
            throw new RuntimeException("Phone number already registered to another seller");
        }

        // Update basic info
        existingSeller.setFirstName(requestDTO.getFirstName());
        existingSeller.setLastName(requestDTO.getLastName());
        existingSeller.setEmail(requestDTO.getEmail());
        existingSeller.setUserName(requestDTO.getUserName());
        existingSeller.setPhone(requestDTO.getPhone());
        existingSeller.setProfileImage(requestDTO.getProfileImage());
        existingSeller.setPreferredContactMethod(requestDTO.getPreferredContactMethod());

        // Update type-specific fields
        if ("COMPANY".equals(existingSeller.getSellerType())) {
            existingSeller.setCompanyName(requestDTO.getCompanyName());
            existingSeller.setBusinessRegistrationNumber(requestDTO.getBusinessRegistrationNumber());
            existingSeller.setBusinessAddress(requestDTO.getBusinessAddress());
            existingSeller.setWebsite(requestDTO.getWebsite());
        } else {
            existingSeller.setOccupation(requestDTO.getOccupation());
            existingSeller.setNationality(requestDTO.getNationality());
            existingSeller.setDateOfBirth(requestDTO.getDateOfBirth());
        }

        // Update password only if provided
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
            existingSeller.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }

        Seller updatedSeller = sellerRepository.save(existingSeller);
        return mapToResponseDTO(updatedSeller);
    }

    // VERIFY SELLER (Admin Only) 
    @Transactional
    public SellerResponseDTO verifySeller(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));
        seller.setIsVerified(true);
        Seller updatedSeller = sellerRepository.save(seller);
        return mapToResponseDTO(updatedSeller);
    }

    //  UNVERIFY SELLER (Admin Only) 
    @Transactional
    public SellerResponseDTO unverifySeller(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));
        seller.setIsVerified(false);
        Seller updatedSeller = sellerRepository.save(seller);
        return mapToResponseDTO(updatedSeller);
    }

    // DELETE SELLER
    @Transactional
    public void deleteSeller(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new RuntimeException("Seller not found with id: " + id);
        }
        sellerRepository.deleteById(id);
    }

    // DEACTIVATE SELLER 
    @Transactional
    public SellerResponseDTO deactivateSeller(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));
        seller.setIsActive(false);
        Seller updatedSeller = sellerRepository.save(seller);
        return mapToResponseDTO(updatedSeller);
    }

    // ACTIVATE SELLER
    @Transactional
    public SellerResponseDTO activateSeller(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));
        seller.setIsActive(true);
        Seller updatedSeller = sellerRepository.save(seller);
        return mapToResponseDTO(updatedSeller);
    }

    // MAP TO RESPONSE DTO 
    private SellerResponseDTO mapToResponseDTO(Seller seller) {
        SellerResponseDTO.SellerResponseDTOBuilder builder = SellerResponseDTO.builder()
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
                .isVerified(seller.getIsVerified())
                .totalPropertiesSold(seller.getTotalPropertieSold())
                .totalPropertiesListed(seller.getTotalPropertiesListed())
                .rating(seller.getRating())
                .registrationDate(seller.getRegistrationDate())
                .preferredContactMethod(seller.getPreferredContactMethod())
                .createdAt(seller.getCreatedAt())
                .updatedAt(seller.getUpdatedAt());

        if ("COMPANY".equals(seller.getSellerType())) {
            builder.companyName(seller.getCompanyName())
                    .businessRegistrationNumber(seller.getBusinessRegistrationNumber())
                    .businessAddress(seller.getBusinessAddress())
                    .website(seller.getWebsite());
        } else {
            builder.occupation(seller.getOccupation())
                    .nationality(seller.getNationality())
                    .dateOfBirth(seller.getDateOfBirth());
        }

        return builder.build();
    }
}