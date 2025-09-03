package com.fefe.user_service.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s'-]+$",
            message = "Name can only contain letters, spaces, hyphens and apostrophes")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s'-]+$",
            message = "Surname can only contain letters, spaces, hyphens and apostrophes")
    private String surname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Size(max = 320, message = "Email cannot exceed 320 characters")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$",
            message = "Phone number must be valid international format")
    private String phoneNumber;

    private Long loyaltyCardNumber;

    @NotNull(message = "Marketing acceptance status is required")
    private Boolean marketingAccepted;
}
