package com.example.restuserservice.mapper;

import com.example.restuserservice.config.MapperConfig;
import com.example.restuserservice.dto.user.UserRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;
import com.example.restuserservice.models.User;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User book);

    User toModel(UserRequestDto requestDto);

    @IterableMapping(elementTargetType = UserResponseDto.class)
    List<UserResponseDto> toDtoList(List<User> userList);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    User partiallyUpdateUser(UserUpdateRequestDto dto, @MappingTarget User user);

    User fullyUpdateUser(UserRequestDto requestDto, @MappingTarget User user);
}
