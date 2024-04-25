package com.example.restuserservice.dto.user;

import com.example.restuserservice.validation.AgeMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @AgeMatch
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;
}
