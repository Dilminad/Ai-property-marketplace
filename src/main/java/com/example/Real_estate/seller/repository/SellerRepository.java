package com.example.Real_estate.seller.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Real_estate.seller.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long>{

    Optional<Seller>findByEmail(String email);
    Optional<Seller>findByUsername(String userName);
    Optional<Seller> findByPhone(String phone);
    List<Seller> findByIsVerified(Boolean isVerified);
    List<Seller> findBySellerType(String sellerType);
    List<Seller> findByRatingGreaterThanEqual(Double rating);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByPhone(String phone);
}
