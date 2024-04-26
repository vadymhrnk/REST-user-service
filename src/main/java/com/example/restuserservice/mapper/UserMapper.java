package com.example.restuserservice.mapper;

import com.example.restuserservice.config.MapperConfig;
import com.example.restuserservice.dto.user.UserRegistrationRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;
import com.example.restuserservice.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User book);

    User toModel(UserRegistrationRequestDto responseDto);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void updateUserFromDto(UserUpdateRequestDto dto, @MappingTarget User user);
}
