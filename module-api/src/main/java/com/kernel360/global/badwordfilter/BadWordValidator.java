package com.kernel360.global.badwordfilter;

import com.kernel360.global.annotation.BadWordFilter;
import com.vane.badwordfiltering.BadWordFiltering;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class BadWordValidator implements ConstraintValidator<BadWordFilter, Object> {
    private BadWordFiltering badWordFiltering;
    @Override
    public void initialize(BadWordFilter constraintAnnotation) {

        badWordFiltering = new BadWordFiltering();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String string) {
            return !badWordFiltering.check(string);
        } else if (value instanceof List<?> list) {
            return handleStringList(list);
        }
        return true;
    }

    private boolean handleStringList(List<?> list) {
        return list.stream()
                .map(String.class::cast)
                .noneMatch(badWordFiltering::check);
    }
}