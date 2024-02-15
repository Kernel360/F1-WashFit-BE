package com.kernel360.washzone.service;

import com.kernel360.washzone.dto.KakaoMapDto;
import com.kernel360.washzone.dto.WashZoneDto;
import com.kernel360.washzone.repository.WashZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WashZoneService {

    private final WashZoneRepository washZoneRepository;

    public List<WashZoneDto> getWashZones(KakaoMapDto kakaoMapDto) {

        return
        washZoneRepository.findWashZonesWithinBounds(
                kakaoMapDto.minx(), kakaoMapDto.maxx(), kakaoMapDto.miny(), kakaoMapDto.maxy())
                .stream().map(WashZoneDto::from)
                .toList();
    }
}

