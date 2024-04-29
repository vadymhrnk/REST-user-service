package com.example.restuserservice.dto.user;

import com.example.restuserservice.validation.AgeMatch;
import com.example.restuserservice.validation.DateBefore;
import com.example.restuserservice.validation.NullOrNotBlank;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserUpdateRequestDto {
    @Email
    private String email;

    @NullOrNotBlank
    private String firstName;

    @NullOrNotBlank
    private String lastName;

    @DateBefore
    @AgeMatch
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;
}
