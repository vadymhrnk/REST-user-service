package com.example.restuserservice.service;

import static org.mockito.Mockito.when;

import com.example.restuserservice.dto.user.UserRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;
import com.example.restuserservice.mapper.UserMapper;
import com.example.restuserservice.models.User;
import com.example.restuserservice.repository.user.UserRepository;
import com.example.restuserservice.service.user.impl.UserServiceImpl;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final long ID_1 = 1L;
    private static final String BOB_EMAIL = "bob@gmail.com";
    private static final String BOB_FIRST_NAME = "Bob";
    private static final String BOB_LAST_NAME = "Smith";
    private static final LocalDate BOB_BIRTH_DATE = LocalDate.of(1995, 5, 12);
    private static final String UPDATED_BOB_EMAIL = "updatedBob@gmail.com";
    private static final String NEW_BOB_EMAIL = "newUserGeorge@gmail.com";
    private static final String NEW_BOB_NAME = "George";
    private static final String NEW_BOB_LAST_NAME = "Willis";
    private static final LocalDate NEW_BOB_BIRTH_DATE = LocalDate.of(2002, 6, 5);
    private static final String ID = "id";

    private static User bobUser;
    private static User updatedBobUser;
    private static User fullyUpdatedBobUser;
    private static UserRequestDto bobUserRequestDto;
    private static UserRequestDto fullyUpdatedBobUserRequestDto;
    private static UserResponseDto bobUserResponseDto;
    private static UserResponseDto updatedBobUserResponseDto;
    private static UserResponseDto fullyUpdatedBobUserResponseDto;
    private static UserUpdateRequestDto bobUserUpdateRequestDto;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void beforeAll() {
        bobUserRequestDto = new UserRequestDto();
        bobUserRequestDto.setEmail(BOB_EMAIL);
        bobUserRequestDto.setFirstName(BOB_FIRST_NAME);
        bobUserRequestDto.setLastName(BOB_LAST_NAME);
        bobUserRequestDto.setBirthDate(BOB_BIRTH_DATE);

        bobUser = new User();
        bobUser.setId(ID_1);
        bobUser.setEmail(BOB_EMAIL);
        bobUser.setFirstName(BOB_FIRST_NAME);
        bobUser.setLastName(BOB_LAST_NAME);
        bobUser.setBirthDate(BOB_BIRTH_DATE);

        bobUserResponseDto = new UserResponseDto();
        bobUserResponseDto.setId(ID_1);
        bobUserResponseDto.setEmail(bobUserRequestDto.getEmail());
        bobUserResponseDto.setFirstName(bobUserRequestDto.getFirstName());
        bobUserResponseDto.setLastName(bobUserRequestDto.getLastName());
        bobUserResponseDto.setBirthDate(bobUserRequestDto.getBirthDate());

        bobUserUpdateRequestDto = new UserUpdateRequestDto();
        bobUserUpdateRequestDto.setEmail(UPDATED_BOB_EMAIL);

        updatedBobUser = new User();
        updatedBobUser.setId(ID_1);
        updatedBobUser.setEmail(UPDATED_BOB_EMAIL);
        updatedBobUser.setFirstName(BOB_FIRST_NAME);
        updatedBobUser.setLastName(BOB_LAST_NAME);
        updatedBobUser.setBirthDate(BOB_BIRTH_DATE);

        updatedBobUserResponseDto = new UserResponseDto();
        updatedBobUserResponseDto.setId(ID_1);
        updatedBobUserResponseDto.setEmail(updatedBobUser.getEmail());
        updatedBobUserResponseDto.setFirstName(updatedBobUser.getFirstName());
        updatedBobUserResponseDto.setLastName(updatedBobUser.getLastName());
        updatedBobUserResponseDto.setBirthDate(updatedBobUser.getBirthDate());

        fullyUpdatedBobUserRequestDto = new UserRequestDto();
        fullyUpdatedBobUserRequestDto.setEmail(NEW_BOB_EMAIL);
        fullyUpdatedBobUserRequestDto.setFirstName(NEW_BOB_NAME);
        fullyUpdatedBobUserRequestDto.setLastName(NEW_BOB_LAST_NAME);
        fullyUpdatedBobUserRequestDto.setBirthDate(NEW_BOB_BIRTH_DATE);

        fullyUpdatedBobUser = new User();
        fullyUpdatedBobUser.setId(ID_1);
        fullyUpdatedBobUser.setEmail(fullyUpdatedBobUserRequestDto.getEmail());
        fullyUpdatedBobUser.setFirstName(fullyUpdatedBobUserRequestDto.getFirstName());
        fullyUpdatedBobUser.setLastName(fullyUpdatedBobUserRequestDto.getLastName());
        fullyUpdatedBobUser.setBirthDate(fullyUpdatedBobUserRequestDto.getBirthDate());

        fullyUpdatedBobUserResponseDto = new UserResponseDto();
        fullyUpdatedBobUserResponseDto.setId(ID_1);
        fullyUpdatedBobUserResponseDto.setEmail(fullyUpdatedBobUser.getEmail());
        fullyUpdatedBobUserResponseDto.setFirstName(fullyUpdatedBobUser.getFirstName());
        fullyUpdatedBobUserResponseDto.setLastName(fullyUpdatedBobUser.getLastName());
        fullyUpdatedBobUserResponseDto.setBirthDate(fullyUpdatedBobUser.getBirthDate());
    }

    @Test
    void register_ValidUser_Successful() {
        when(userRepository.findByEmail(BOB_EMAIL)).thenReturn(Optional.empty());
        when(userMapper.toModel(bobUserRequestDto)).thenReturn(bobUser);
        when(userRepository.save(bobUser)).thenReturn(bobUser);
        when(userMapper.toDto(bobUser)).thenReturn(bobUserResponseDto);

        UserResponseDto expected = bobUserResponseDto;

        UserResponseDto actual = userService.register(bobUserRequestDto);

        Assertions.assertNotNull(actual);
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, ID));
    }

    @Test
    void partiallyUpdateUser_ValidData_Successful() {
        when(userRepository.findById(ID_1)).thenReturn(Optional.of(bobUser));
        when(userMapper.partiallyUpdateUser(bobUserUpdateRequestDto, bobUser))
                .thenReturn(updatedBobUser);
        when(userRepository.save(updatedBobUser)).thenReturn(updatedBobUser);
        when(userMapper.toDto(updatedBobUser)).thenReturn(updatedBobUserResponseDto);

        UserResponseDto expected = updatedBobUserResponseDto;

        UserResponseDto actual = userService.partiallyUpdateUser(ID_1, bobUserUpdateRequestDto);

        Assertions.assertNotNull(actual);
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, ID));
    }

    @Test
    void updateFullUser_ValidData_Successful() {
        when(userRepository.findById(ID_1)).thenReturn(Optional.of(bobUser));
        when(userMapper.fullyUpdateUser(fullyUpdatedBobUserRequestDto, bobUser))
                .thenReturn(fullyUpdatedBobUser);
        when(userRepository.save(fullyUpdatedBobUser)).thenReturn(fullyUpdatedBobUser);
        when(userMapper.toDto(fullyUpdatedBobUser)).thenReturn(fullyUpdatedBobUserResponseDto);

        UserResponseDto expected = fullyUpdatedBobUserResponseDto;

        UserResponseDto actual = userService.updateFullUser(ID_1, fullyUpdatedBobUserRequestDto);

        Assertions.assertNotNull(actual);
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, ID));
    }
}
