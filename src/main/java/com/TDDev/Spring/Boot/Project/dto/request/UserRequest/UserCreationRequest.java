package com.TDDev.Spring.Boot.Project.dto.request.UserRequest;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.TDDev.Spring.Boot.Project.validator.DobValidator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String username;

    @Size(min = 6, message = "MIN_INVALID_PASSWORD")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;
}
