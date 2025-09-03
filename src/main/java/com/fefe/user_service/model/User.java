package com.fefe.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    private Long loyaltyCardNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean marketingAccepted;

    // ===== WALIDACJA DOMENOWA (BIZNESOWA) =====
//    public void validateBusinessRules() {
//        if (name == null || name.trim().isEmpty()) {
//            throw new ValidationException("Name cannot be empty");
//        }
//        if (email == null || !isValidEmail(email)) {
//            throw new ValidationException("Email is invalid");
//        }
//        // inne reguły biznesowe...
//    }

    @PrePersist
    @PreUpdate
    private void validateBeforePersist() {
//        validateBusinessRules();
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}