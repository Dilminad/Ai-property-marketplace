package com.example.Real_estate.seller.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Real_estate.seller.dto.SellerResponseDTO;
import com.example.Real_estate.seller.service.SellerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    
    //Register Seller
    @PostMapping("/api/sellers")
    public ResponseEntity<SellerResponseDTO> registerSeller<@Valid @RequestBody
}
