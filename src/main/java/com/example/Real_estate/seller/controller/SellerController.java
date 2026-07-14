package com.example.Real_estate.seller.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Real_estate.seller.dto.SellerRequestDTO;
import com.example.Real_estate.seller.dto.SellerResponseDTO;
import com.example.Real_estate.seller.service.SellerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    
    // Register Seller
    @PostMapping("/register")
    public ResponseEntity<SellerResponseDTO> registerSeller(@Valid @RequestBody SellerRequestDTO requestDTO) {
        SellerResponseDTO response = sellerService.createSeller(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Get All Sellers
    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>>getAllSellers(){
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    //Get Seller By id
    @GetMapping("/id")
    public ResponseEntity<SellerResponseDTO>getSellerById(@PathVariable Long id){
        return ResponseEntity.ok(sellerService.getSellerById(id));

        
    }
}