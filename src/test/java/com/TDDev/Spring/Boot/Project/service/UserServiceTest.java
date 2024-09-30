package com.TDDev.Spring.Boot.Project.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserCreationRequest;
import com.TDDev.Spring.Boot.Project.dto.response.RoleResponse;
import com.TDDev.Spring.Boot.Project.dto.response.UserResponse;
import com.TDDev.Spring.Boot.Project.entity.Role;
import com.TDDev.Spring.Boot.Project.entity.User;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.exception.ErrorCode;
import com.TDDev.Spring.Boot.Project.repository.RoleRepository;
import com.TDDev.Spring.Boot.Project.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);

        request = UserCreationRequest.builder()
                .username("usertest")
                .password("123456")
                .firstName("first name test")
                .lastName("last name test")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("1ce9ae4a-0973-44a4-82c5-0d0cd8bb4ee8")
                .username("usertest")
                .firstName("first name test")
                .lastName("last name test")
                .dob(dob)
                .role(RoleResponse.builder().name("CUSTOMER").build())
                .build();

        user = User.builder()
                .id("1ce9ae4a-0973-44a4-82c5-0d0cd8bb4ee8")
                .username("usertest")
                .firstName("first name test")
                .lastName("last name test")
                .dob(dob)
                .role(roleRepository
                        .findById("CUSTOMER")
                        .orElse(Role.builder().name("CUSTOMER").build()))
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // Given
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(roleRepository.findById("CUSTOMER"))
                .thenReturn(Optional.of(Role.builder().name("CUSTOMER").build()));

        // When
        var result = userService.createUser(request);

        // Then
        Assertions.assertThat(result.getId()).isEqualTo("1ce9ae4a-0973-44a4-82c5-0d0cd8bb4ee8");
        Assertions.assertThat(result.getUsername()).isEqualTo("usertest");
    }

    @Test
    void createUser_userExisted_fail() {
        // Given
        Mockito.when(userRepository.save(Mockito.any())).thenThrow(new AppException(ErrorCode.USER_EXISTED));
        Mockito.when(roleRepository.findById("CUSTOMER"))
                .thenReturn(Optional.of(Role.builder().name("CUSTOMER").build()));

        // When
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_EXISTED);
    }

    @Test
    @WithMockUser(username = "usertest")
    void getMyInfo_valid_success() {
        // Given
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getId()).isEqualTo("1ce9ae4a-0973-44a4-82c5-0d0cd8bb4ee8");
        Assertions.assertThat(response.getUsername()).isEqualTo("usertest");
    }
}
