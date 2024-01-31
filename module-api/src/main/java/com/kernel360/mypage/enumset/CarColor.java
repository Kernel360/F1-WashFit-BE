package com.kernel360.mypage.enumset;

import java.util.List;

public enum CarColor {

    WHITE("white", "#FFFFFF"),
    RAT_COLOR("rat color", "#808080"),
    BLACK("black", "#37383C"),
    RED("red", "#FF4500"),
    YELLOW("yellow", "#FFD400"),
    GREEN("green", "#2F4F4F"),
    BLUE("blue", "#145B7D"),
    OTHER("Other", "#6E0000");

    private final String label;
    private final String value;

    CarColor(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static List<CarColor> getAllColors() {
        return List.of(values());
    }}
