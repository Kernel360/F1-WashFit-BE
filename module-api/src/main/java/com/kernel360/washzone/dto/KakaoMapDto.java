package com.kernel360.washzone.dto;

public record KakaoMapDto(
        Double minX,
        Double minY,
        Double maxX,
        Double maxY,
        Integer level
) {
}
