package com.kernel360.mypage.enumset;

import java.util.List;

public enum CarParking {

    HOUSE("Indoor/Underground", "house"),
    ROAD("노상", "road"),
    PILOTI("Piloti", "piloti");

    private final String label;
    private final String value;

    CarParking(String label, String value) {
        this.label = label;
        this.value = value;
    }

    // Getters
    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static List<CarParking> getAllParkings() {
        return List.of(values());
    }
}
