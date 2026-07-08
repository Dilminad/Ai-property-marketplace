package com.example.Real_estate.admin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Real_estate.admin.dto.AdminRequestDTO;
import com.example.Real_estate.admin.dto.AdminResponseDTO;
import com.example.Real_estate.admin.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    // Constructor injection
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Create admin - FIXED: removed extra mapping
    @PostMapping("/register")
    public ResponseEntity<AdminResponseDTO> registerAdmin(@Valid @RequestBody AdminRequestDTO requestDTO) {
        AdminResponseDTO response = adminService.createAdmin(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all Admins
    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // Get Admin by id
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    // Get Admin by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<AdminResponseDTO> getAdminByEmail(@PathVariable String email) {
        return ResponseEntity.ok(adminService.getAdminByEmail(email));
    }

    // Update Admin
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> updateAdminEntity(
            @PathVariable Long id,
            @Valid @RequestBody AdminRequestDTO requestDTO) {
        return ResponseEntity.ok(adminService.updateAdmin(id, requestDTO));
    }

    // Delete Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}