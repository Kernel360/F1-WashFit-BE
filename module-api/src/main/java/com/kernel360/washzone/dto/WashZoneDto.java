package com.kernel360.washzone.dto;

import com.kernel360.washzone.entity.WashZone;


/**
 * DTO for {@link com.kernel360.washzone.entity.WashZone}
 */
public record WashZoneDto(
        String name,
        String address,
        Double latitude,
        Double longitude,
        String type,
        String remarks) {

    public static WashZoneDto of (
            String name,
            String address,
            Double latitude,
            Double longitude,
            String type,
            String remarks
    ){
        return new WashZoneDto(
                        name,
                        address,
                        latitude,
                        longitude,
                        type,
                        remarks);
    }
    public static WashZoneDto from(WashZone washZone) {

        return WashZoneDto.of(
                    washZone.getName(),
                    washZone.getAddress(),
                    washZone.getLatitude(),
                    washZone.getLongitude(),
                    washZone.getType(),
                    washZone.getRemarks());
    }

    public WashZone toEntity(){

        return WashZone.of(
                this.name,
                this.address,
                this.latitude,
                this.longitude,
                this.type,
                this.remarks
        );
    }
}