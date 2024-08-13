package com.ssafy.top.global.exception.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = BooleanValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface AllowedBoolean {
    String message() default "Value must be true or false";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

