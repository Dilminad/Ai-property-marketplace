package com.example.Real_estate.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Real_estate.admin.entity.Admin;
import java.util.List;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByUserName(String userName);
    Optional<Admin> findByPhone(String phone);

    boolean existsByEmail(String email);
    boolean existsByUserName (String userName);
    boolean existsByPhone(String phone);
    

}
