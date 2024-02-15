package com.kernel360.washzone.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wash_zone")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WashZone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "washZone_id_gen")
    @SequenceGenerator(name = "washZone_id_gen", sequenceName = "wash_zone_washZone_no_seq")
    @Column(name = "washZone_no", nullable = false)
    private Long washZoneNo;

    @Column(name = "washZone_name", nullable = false)
    private String name;

    @Column(name = "washZone_address", nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = true)
    private String type; // This and the next field can be null as per your description

    @Column(nullable = true)
    private String remarks;

}
