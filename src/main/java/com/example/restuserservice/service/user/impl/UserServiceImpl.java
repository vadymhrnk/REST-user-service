package com.example.restuserservice.service.user.impl;

import com.example.restuserservice.dto.user.UserRegistrationRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.exception.RegistrationException;
import com.example.restuserservice.mapper.UserMapper;
import com.example.restuserservice.models.User;
import com.example.restuserservice.repository.user.UserRepository;
import com.example.restuserservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String REGISTER_ERROR_MESSAGE = "Can't register a new user with email: ";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(REGISTER_ERROR_MESSAGE + requestDto.getEmail()
            );
        }
        User user = userMapper.toModel(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
