package com.kernel360.mypage.enumset;

import java.util.List;

public enum CarType {

    COMPACT_CAR("compact car", "micro"),
    SMALL("small", "subcompact"),
    MEDIUM("medium", "compact"),
    LARGE("large", "fullsize");

    private final String label;
    private final String value;

    CarType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static List<CarType> getAllTypes() {
        return List.of(values());
    }
}
