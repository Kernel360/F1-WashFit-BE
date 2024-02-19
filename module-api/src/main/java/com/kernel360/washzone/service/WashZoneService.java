package com.kernel360.washzone.service;

import com.kernel360.washzone.dto.KakaoMapDto;
import com.kernel360.washzone.dto.WashZoneDto;
import com.kernel360.washzone.entity.WashZone;
import com.kernel360.washzone.repository.WashZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WashZoneService {

    private final WashZoneRepository washZoneRepository;

    @Transactional(readOnly = true)
    public List<WashZoneDto> getWashZones(KakaoMapDto kakaoMapDto) {

        return
                washZoneRepository.findWashZonesWithinBounds(
                                kakaoMapDto.minX(), kakaoMapDto.maxY(), kakaoMapDto.minY(), kakaoMapDto.maxY())
                        .stream().map(WashZoneDto::from)
                        .toList();
    }

    @Transactional(readOnly = true)
    public Page<WashZoneDto> getWashZonesByKeyword(String keyword, Pageable pageable) {

        return washZoneRepository.findByKeyword(keyword, pageable).map(WashZoneDto::from);
    }

    @Transactional
    public WashZone save(WashZoneDto washZoneDto) {

        return washZoneRepository.save(washZoneDto.toEntity());
    }
}

