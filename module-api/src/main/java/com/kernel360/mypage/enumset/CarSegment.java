package com.kernel360.mypage.enumset;

import java.util.List;

public enum CarSegment {

    SEDAN("세단", "sedan"),
    HATCHBACK("해치백", "hatchback"),
    SUV("SUV", "suv"),
    ETC("기타", "etc");
    private final String label;
    private final String value;

    CarSegment(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static List<CarSegment> getAllSegments() {
        return List.of(values());
    }
}
