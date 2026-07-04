package com.example.Real_estate.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequestDTO {
    @NotBlank(message = "First name Required")
    private String firstName;

    @NotBlank(message = "Last name is Required")
    private String lastName;

    @NotBlank(message = "Email is Required")
    @Email(message = "Inavalid Email Format")
    private String email;

    @NotBlank(message = "Username is Required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String userName;

    @NotBlank(message = "password is Required")
    @Size(min = 6, message = "Password must be at lest 6 characters")
    private String password;

    @NotBlank(message = "Number is required")
    @Pattern(regexp ="^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    private String profileImage;
}
