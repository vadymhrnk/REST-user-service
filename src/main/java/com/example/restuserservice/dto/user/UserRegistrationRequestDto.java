package com.example.restuserservice.dto.user;

import com.example.restuserservice.validation.AgeMatch;
import com.example.restuserservice.validation.DateBefore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @DateBefore
    @AgeMatch
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;
}
