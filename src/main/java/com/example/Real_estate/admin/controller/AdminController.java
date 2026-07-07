package com.example.Real_estate.admin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Real_estate.admin.dto.AdminRequestDTO;
import com.example.Real_estate.admin.dto.AdminResponseDTO;
import com.example.Real_estate.admin.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;

    //create admin
    @PostMapping("/register")
    public ResponseEntity<AdminResponseDTO>registerAdmin(@Valid @RequestBody AdminRequestDTO requestDTO){
        AdminResponseDTO response =adminService.createAdmin(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Get all Admins
    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmins(){
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    //Get Admin by id 
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> updateAdmin(
        @PathVariable Long id,
        @Valid @RequestBody AdminRequestDTO requestDTO) {
            return ResponseEntity.ok(adminService.updateAdmin(id, requestDTO));
            
        }
    //Get Admin by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<AdminResponseDTO> getAdminByEmail(@PathVariable String email){
        return ResponseEntity.ok(adminService.getAdminByEmail(email));

    }

    //update Admin
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO>updateAdminEntity(
    @PathVariable Long id,
    @Valid @RequestBody AdminRequestDTO requestDTO) {
        return ResponseEntity.ok(adminService.updateAdmin(id, requestDTO));
    }

    //Delete Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        }
    
    
}
