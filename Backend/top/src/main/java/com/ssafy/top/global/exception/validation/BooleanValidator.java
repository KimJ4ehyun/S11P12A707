package com.ssafy.top.global.exception.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BooleanValidator implements ConstraintValidator<AllowedBoolean, Boolean> {
    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        return value != null && (value.equals(true) || value.equals(false));
    }
}
