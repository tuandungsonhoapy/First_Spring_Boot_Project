package com.TDDev.Spring.Boot.Project.dto.request.UserRequest;

import java.time.LocalDate;
import java.util.List;

import com.TDDev.Spring.Boot.Project.validator.DobValidator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

    String role;
}
