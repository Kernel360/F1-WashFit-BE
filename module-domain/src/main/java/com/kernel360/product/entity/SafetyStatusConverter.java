package com.kernel360.product.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SafetyStatusConverter implements AttributeConverter<SafetyStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SafetyStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public SafetyStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(SafetyStatus.values())
                     .filter(safetyStatus -> safetyStatus.getCode().equals(dbData))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }
}
