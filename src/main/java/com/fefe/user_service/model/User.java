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

    @Column(unique = true, nullable = false)
    private String email;

    private String name;
    private String surname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "loyalty_card_number", insertable = false,  updatable = false)
    private Long loyaltyCardNumber;

    @Column(name = "marketing_accepted", nullable = false)
    private Boolean marketingAccepted;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    @PreUpdate
    private void validateBeforePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}