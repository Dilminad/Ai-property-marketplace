package com.example.Real_estate.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Customer extends User {
     private String preferredPropertyType;

     private Double minBudget;

     private Double maxBudget;

     private String preferredLocation;

     private Integer preferredBedrooms;

     private Integer preferredBathrooms;

     private String buyerTYpe;

     private String dateOfBirth;

     private String ocupation;

     private String nationality;
}
