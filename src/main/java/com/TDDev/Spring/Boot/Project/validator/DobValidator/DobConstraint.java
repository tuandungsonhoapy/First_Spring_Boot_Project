package com.TDDev.Spring.Boot.Project.validator.DobValidator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "Invalid date of birth!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default Integer.MAX_VALUE;
}
