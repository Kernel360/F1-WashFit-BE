package com.kernel360.main.dto;

public record BannerDto (
        Long id,
        String imageSource,
        String alt
) {
    public static BannerDto of(
            Long id,
            String imageSource,
            String alt
    ) {
        return new BannerDto(
                id,
                imageSource,
                alt
        );
    }
}
