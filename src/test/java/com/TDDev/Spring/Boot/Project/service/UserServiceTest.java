package com.TDDev.Spring.Boot.Project.service;

import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserCreationRequest;
import com.TDDev.Spring.Boot.Project.dto.response.UserResponse;
import com.TDDev.Spring.Boot.Project.entity.User;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData(){
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
                .build();

        user = User.builder()
                .id("1ce9ae4a-0973-44a4-82c5-0d0cd8bb4ee8")
                .username("usertest")
                .firstName("first name test")
                .lastName("last name test")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        // Given
        Mockito.when(userRepository.existsByUsername(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any()))
                .thenReturn(user);

        //When
        var result = userService.createUser(request);

        //Then
        Assertions.assertThat(result.getId().equals("1ce9ae4a-0973-44a4-82c5-0d0cd8bb4ee8"));
        Assertions.assertThat(result.getUsername().equals("usertest"));
    }

    @Test
    void createUser_userExisted_fail(){
        // Given
        Mockito.when(userRepository.existsByUsername(Mockito.anyString()))
                .thenReturn(true);

        //When
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }
}
