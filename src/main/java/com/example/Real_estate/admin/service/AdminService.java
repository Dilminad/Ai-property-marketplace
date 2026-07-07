package com.example.Real_estate.admin.service;

import java.util.List;
import java.util.stream.Collectors;

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
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .phone(requestDTO.getPhone())
                .profileImage(requestDTO.getProfileImage())
                .build();

        Admin savedAdmin = adminRepository.save(admin);
        return mapToResponseDTO(savedAdmin);
    }

    private AdminResponseDTO mapToResponseDTO(Admin admin) {
        return AdminResponseDTO.builder()
                .id(admin.getId())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .userName(admin.getUserName())
                .phone(admin.getPhone())
                .profileImage(admin.getProfileImage())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }

    public AdminResponseDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        return mapToResponseDTO(admin);
    }

    //  get admin by email
    public AdminResponseDTO getAdminByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found with email: " + email));
        return mapToResponseDTO(admin);
    }

    // get all admins
    public List<AdminResponseDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //  update admin
    @Transactional
    public AdminResponseDTO updateAdmin(Long id, AdminRequestDTO requestDTO) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));

        
        if (!existingAdmin.getEmail().equals(requestDTO.getEmail()) 
                && adminRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already registered to another admin");
        }

      
        if (!existingAdmin.getUserName().equals(requestDTO.getUserName()) 
                && adminRepository.existsByUserName(requestDTO.getUserName())) {
            throw new RuntimeException("Username already taken by another admin");
        }

       
        if (!existingAdmin.getPhone().equals(requestDTO.getPhone()) 
                && adminRepository.existsByPhone(requestDTO.getPhone())) {
            throw new RuntimeException("Phone number already registered to another admin");
        }

      
        existingAdmin.setFirstName(requestDTO.getFirstName());
        existingAdmin.setLastName(requestDTO.getLastName());
        existingAdmin.setEmail(requestDTO.getEmail());
        existingAdmin.setUserName(requestDTO.getUserName());
        existingAdmin.setPhone(requestDTO.getPhone());
        existingAdmin.setProfileImage(requestDTO.getProfileImage());

        
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
            existingAdmin.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }

        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return mapToResponseDTO(updatedAdmin);
    }

    // Delete admin method
    @Transactional
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }
}