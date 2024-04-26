package com.example.restuserservice.service.user;

import com.example.restuserservice.dto.user.UserRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserResponseDto register(UserRequestDto requestDto);

    UserResponseDto partiallyUpdateUser(Long userId, UserUpdateRequestDto userUpdateRequestDto);

    UserResponseDto updateFullUser(Long userId, UserRequestDto requestDto);

    List<UserResponseDto> findUsersByBirthDateRange(LocalDate from, LocalDate to);

    void deleteById(Long userId);
}
