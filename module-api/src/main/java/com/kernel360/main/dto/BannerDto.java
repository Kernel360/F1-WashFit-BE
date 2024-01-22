package com.kernel360.main.dto;

public record BannerDto (
        Long id,
        String src,
        String alt
) {
    public static BannerDto of(
            Long id,
            String image,
            String alt
    ) {
        return new BannerDto(
                id,
                image,
                alt
        );
    }
}
