package com.example.restuserservice.controller;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.restuserservice.dto.user.UserRequestDto;
import com.example.restuserservice.dto.user.UserResponseDto;
import com.example.restuserservice.dto.user.UserUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    protected static MockMvc mockMvc;

    private static final long BOB_ID = 1L;
    private static final String BOB_EMAIL = "bob@gmail.com";
    private static final String BOB_FIRST_NAME = "Bob";
    private static final String BOB_LAST_NAME = "Smith";
    private static final LocalDate BOB_BIRTH_DATE = LocalDate.of(1995, 5, 12);

    private static final String DAVID_NEW_EMAIL = "newEmail@yahoo.com";
    private static final String DAVID_FIRST_NAME = "David";
    private static final String DAVID_LAST_NAME = "Wilson";
    private static final LocalDate DAVID_BIRTH_DATE = LocalDate.of(1988, 7, 10);
    private static final String DAVID_ADDRESS = "567 Pine St";
    private static final String DAVID_PHONE_NUMBER = "+380507652211";

    private static final String USERS = "/users/";
    private static final String CLASSPATH_DATABASE_USERS = "classpath:database" + USERS;
    private static final String DELETE_USERS_FROM_USERS_TABLE_SQL = CLASSPATH_DATABASE_USERS
            + "delete-users-from-users-table.sql";
    private static final String ADD_USERS_TO_USERS_TABLE_SQL = CLASSPATH_DATABASE_USERS
            + "add-users-to-users-table.sql";
    private static final String REGISTRATION = USERS + "registration";
    private static final String USERS_ID_5 = USERS + "5";
    private static final String USERS_ID_1 = USERS + "1";

    private static final String BIRTH_DATE_RANGE = USERS
            + "birthDateRange?from=1990-01-01&to=2000-12-31";

    private static final String ID = "id";
    private static final int EXPECTED_LENGTH = 2;
    private static final Long[] USER_IDS = {1L, 4L};

    private static UserRequestDto bobUserRequestDto;
    private static UserResponseDto bobUserResponseDto;
    private static UserUpdateRequestDto davidUserUpdateRequestDto;
    private static UserResponseDto davidUserResponseDto;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();

        bobUserRequestDto = new UserRequestDto();
        bobUserRequestDto.setEmail(BOB_EMAIL);
        bobUserRequestDto.setFirstName(BOB_FIRST_NAME);
        bobUserRequestDto.setLastName(BOB_LAST_NAME);
        bobUserRequestDto.setBirthDate(BOB_BIRTH_DATE);

        bobUserResponseDto = new UserResponseDto();
        bobUserResponseDto.setId(BOB_ID);
        bobUserResponseDto.setEmail(bobUserRequestDto.getEmail());
        bobUserResponseDto.setFirstName(bobUserRequestDto.getFirstName());
        bobUserResponseDto.setLastName(bobUserRequestDto.getLastName());
        bobUserResponseDto.setBirthDate(bobUserRequestDto.getBirthDate());

        davidUserUpdateRequestDto = new UserUpdateRequestDto();
        davidUserUpdateRequestDto.setEmail(DAVID_NEW_EMAIL);

        davidUserResponseDto = new UserResponseDto();
        davidUserResponseDto.setEmail(davidUserUpdateRequestDto.getEmail());
        davidUserResponseDto.setFirstName(DAVID_FIRST_NAME);
        davidUserResponseDto.setLastName(DAVID_LAST_NAME);
        davidUserResponseDto.setBirthDate(DAVID_BIRTH_DATE);
        davidUserResponseDto.setAddress(DAVID_ADDRESS);
        davidUserResponseDto.setPhoneNumber(DAVID_PHONE_NUMBER);
    }

    @Test
    @Sql(
            scripts = DELETE_USERS_FROM_USERS_TABLE_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void register_ValidUser_ShouldReturnUserResponseDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        post(REGISTRATION)
                                .content(objectMapper.writeValueAsString(bobUserRequestDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        UserResponseDto expected = bobUserResponseDto;

        UserResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDto.class
        );

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, ID));
    }

    @Test
    @Sql(
            scripts = ADD_USERS_TO_USERS_TABLE_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = DELETE_USERS_FROM_USERS_TABLE_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void searchUsersByBirthDateRange_ValidDates_ShouldReturnUserResponseList() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(BIRTH_DATE_RANGE).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDto[].class
        );

        List<Long> userIds = Arrays.stream(actual).map(UserResponseDto::getId).toList();

        Assertions.assertEquals(EXPECTED_LENGTH, actual.length);
        assertThat(userIds).containsExactlyInAnyOrder(USER_IDS);
    }

    @Test
    @Sql(
            scripts = ADD_USERS_TO_USERS_TABLE_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = DELETE_USERS_FROM_USERS_TABLE_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void partiallyUpdateUser_ValidData_ShouldReturnUserResponseDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        patch(USERS_ID_5)
                                .content(objectMapper.writeValueAsString(davidUserUpdateRequestDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto expected = davidUserResponseDto;

        UserResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDto.class
        );

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, ID));
    }

    @Test
    @Sql(
            scripts = ADD_USERS_TO_USERS_TABLE_SQL,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = DELETE_USERS_FROM_USERS_TABLE_SQL,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void updateFullUserProfile_ValidData_ShouldReturnUserResponseDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        put(USERS_ID_1)
                                .content(objectMapper.writeValueAsString(bobUserRequestDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto expected = bobUserResponseDto;

        UserResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDto.class
        );

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, ID));
    }
}
