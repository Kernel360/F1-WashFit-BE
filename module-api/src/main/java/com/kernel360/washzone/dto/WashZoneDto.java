package com.kernel360.washzone.dto;

import com.kernel360.washzone.entity.WashZone;


/**
 * DTO for {@link com.kernel360.washzone.entity.WashZone}
 */
public record WashZoneDto(
        Long washZoneNo,
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
                        null,
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

    /** find review **/
    public static WashZoneDto of(
            Long washZoneNo,
            String name,
            String address,
            String type
    ){
        return new WashZoneDto(
                washZoneNo,
                name,
                address,
                null,
                null,
                type,
                null);
    }
}