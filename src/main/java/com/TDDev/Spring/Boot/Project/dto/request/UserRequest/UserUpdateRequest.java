package com.TDDev.Spring.Boot.Project.dto.request.UserRequest;

import com.TDDev.Spring.Boot.Project.validator.DobValidator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;
    LocalDate dob;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    List<String> roles;
}
