package com.example.restuserservice.service.user.impl;

import com.example.restuserservice.dto.user.UserRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;
import com.example.restuserservice.exception.EntityNotFoundException;
import com.example.restuserservice.exception.RegistrationException;
import com.example.restuserservice.mapper.UserMapper;
import com.example.restuserservice.models.User;
import com.example.restuserservice.repository.user.UserRepository;
import com.example.restuserservice.service.user.UserService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String REGISTER_ERROR_MESSAGE = "Can't register a new user with email: ";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(REGISTER_ERROR_MESSAGE + requestDto.getEmail()
            );
        }
        User user = userMapper.toModel(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto partiallyUpdateUser(
            Long userId,
            UserUpdateRequestDto userUpdateRequestDto
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find user by id: " + userId)
                );

        User updatedUser = userMapper.partiallyUpdateUser(userUpdateRequestDto, user);

        userRepository.save(updatedUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserResponseDto updateFullUser(Long userId, UserRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find user by id: " + userId)
                );

        User updatedUser = userMapper.fullyUpdateUser(requestDto, user);

        userRepository.save(updatedUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public List<UserResponseDto> findUsersByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException(
                    "[%s] date must be less than [%s] date".formatted(from, to)
            );
        }

        List<User> users = userRepository.findByBirthDateBetween(from, to);

        return userMapper.toDtoList(users);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
