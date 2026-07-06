package com.example.Real_estate.admin.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Real_estate.admin.dto.AdminRequestDTO;
import com.example.Real_estate.admin.dto.AdminResponseDTO;
import com.example.Real_estate.admin.entity.Admin;
import com.example.Real_estate.admin.repository.AdminRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AdminResponseDTO createAdmin(AdminRequestDTO requestDTO) {
        // check admin is already exists
        if (adminRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        if (adminRepository.existsByUserName(requestDTO.getUserName())) {
            throw new RuntimeException("Username already taken");
        }
        if (adminRepository.existsByPhone(requestDTO.getPhone())) {
            throw new RuntimeException("Phone number already registered");

        }

        Admin admin = Admin.builder()
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .userName(requestDTO.getUserName())
                .password(requestDTO.getPassword())
                .phone(requestDTO.getPhone())
                .profileImage(requestDTO.getProfileImage())
                .build();


        Admin savedAdmin = adminRepository.save(admin);
        return mapToResponseDTO(savedAdmin);

    }
}
