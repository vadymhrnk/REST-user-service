package com.example.restuserservice.mapper;

import com.example.restuserservice.config.MapperConfig;
import com.example.restuserservice.dto.user.UserRegistrationRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.models.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User book);

    User toModel(UserRegistrationRequestDto responseDto);
}
