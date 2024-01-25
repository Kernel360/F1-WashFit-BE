package com.kernel360.mypage.enumset;

import java.util.List;

public enum CarDriving {

    CITY_CENTER("city center", "comport"),
    HIGH_SPEED("High speed", "highway"),
    COMPOSITE("composite", "complex");

    private final String label;
    private final String value;

    CarDriving(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static List<CarDriving> getAllDrivings() {
        return List.of(values());
    }
}
