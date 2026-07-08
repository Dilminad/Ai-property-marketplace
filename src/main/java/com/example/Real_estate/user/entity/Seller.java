package com.example.Real_estate.user.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "sellers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Seller extends User {

    @Column(nullable = false)
    private String sellerType;

    //company sellers 
    private String companyName;

    private String businessRegistrationNumber;

    private String taxId;

    private String businessAddress;

    private String website;

    //induvidual sellers
    private  String occupation;

    private String nationality;

    private String dateOfBirth;


    private Boolean isVerified;

    private Integer totalPropertiesSold;

    private Integer totalPropertiesListed;

    private Double rating = 0.0;

    private LocalDate registrationDate;

    private String preferredContactMethod;

    private String idProof;

    private String addressProof;


}
