package com.fefe.user_service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUserResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private Long loyaltyCardNumber;
    private LocalDateTime createdAt;
    private Boolean marketingAccepted;
}
