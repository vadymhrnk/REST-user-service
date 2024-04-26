package com.example.restuserservice.controller;

import com.example.restuserservice.dto.user.UserRegistrationRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;
import com.example.restuserservice.exception.RegistrationException;
import com.example.restuserservice.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User manager", description = "Endpoint to manage users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "Register a new user")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PatchMapping("/{userId}")
    @Operation(summary = "Update an existing user", description = "Update an existing user")
    public UserResponseDto partiallyUpdateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto
    ) {
        return userService.partiallyUpdateUser(userId, userUpdateRequestDto);
    }
}
