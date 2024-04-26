package com.example.restuserservice.service.user;

import com.example.restuserservice.dto.user.UserRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;

public interface UserService {
    UserResponseDto register(UserRequestDto requestDto);

    UserResponseDto partiallyUpdateUser(Long userId, UserUpdateRequestDto userUpdateRequestDto);

    UserResponseDto updateFullUser(Long userId, UserRequestDto requestDto);

    void deleteById(Long userId);
}
