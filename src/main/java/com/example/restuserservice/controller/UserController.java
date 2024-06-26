package com.example.restuserservice.controller;

import com.example.restuserservice.dto.user.UserRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;
import com.example.restuserservice.exception.RegistrationException;
import com.example.restuserservice.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public UserResponseDto register(@RequestBody @Valid UserRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @GetMapping("/birthDateRange")
    @Operation(
            summary = "Search users by birth date range",
            description = "Search users within a specified birth date range"
    )
    public List<UserResponseDto> searchUsersByBirthDateRange(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return userService.findUsersByBirthDateRange(from, to);
    }

    @PatchMapping("/{userId}")
    @Operation(
            summary = "Update an existing user",
            description = "Allows users to partially update an existing user"
    )
    public UserResponseDto partiallyUpdateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto
    ) {
        return userService.partiallyUpdateUser(userId, userUpdateRequestDto);
    }

    @PutMapping("/{userId}")
    @Operation(
            summary = "Update user profile",
            description = "Allows users to update their profile information"
    )
    public UserResponseDto updateFullUserProfile(
            @PathVariable Long userId,
            @RequestBody @Valid UserRequestDto requestDto
    ) {
        return userService.updateFullUser(userId, requestDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user by id", description = "Delete user by id")
    public void delete(@PathVariable Long userId) {
        userService.deleteById(userId);
    }
}
