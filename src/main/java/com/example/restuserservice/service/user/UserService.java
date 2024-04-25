package com.example.restuserservice.service.user;

import com.example.restuserservice.dto.user.UserRegistrationRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
