package com.example.Real_estate.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Real_estate.user.entity.Seller;
import java.util.List;


public interface SellerRepository extends JpaRepository<Seller, Long >{

    //find by email
    Optional<Seller>findByEmail (String email);

    //find by username
    Optional<Seller>findByUsername (String userName);

    //find by phone
    Optional <Seller> findByPhone(String phone );

    //check existance
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByPhone(String phone);

    //find by type
    List<Seller>findBySellerType (String sellerType);

    //find activ
}
