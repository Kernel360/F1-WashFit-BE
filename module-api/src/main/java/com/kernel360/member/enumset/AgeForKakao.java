package com.kernel360.member.enumset;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AgeForKakao {
    TWENTY("20~29", 40),
    THIRTY("30~39", 30),
    FORTY("40~49", 40),
    FIFTY("50~59", 50),
    SIXTY("60~69", 60);

    private final String avg;
    private final int value;

    public static AgeForKakao fromString(String text) {
        for (AgeForKakao value : AgeForKakao.values()) {
            if (value.avg.equalsIgnoreCase(text)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
