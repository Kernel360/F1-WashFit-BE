package com.kernel360.washzone.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.washzone.code.WashZoneErrorCode;
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
        if (IsDuplicated(washZoneDto))
            throw new BusinessException(WashZoneErrorCode.DUPLICATED_WAHSZONE_INFO);

        return washZoneRepository.save(washZoneDto.toEntity());
    }

    private boolean IsDuplicated(WashZoneDto washZoneDto) {

        return washZoneRepository.existsByAddress(washZoneDto.address());
    }


    @Transactional
    public int saveBulk(List<WashZoneDto> washZoneDtoList) {
        List<WashZone> list = washZoneDtoList.stream().map(WashZoneDto::toEntity).toList();
        List<WashZone> validatedList = validateDuplicated(list);
        if (validatedList.isEmpty()){
            throw new BusinessException(WashZoneErrorCode.DUPLICATED_WAHSZONE_INFO);
        }
        washZoneRepository.saveAll(validatedList);

        return validatedList.size();
    }

    private List<WashZone> validateDuplicated(List<WashZone> washZoneList) {

        return washZoneList.stream()
                .filter(w -> !washZoneRepository.existsByAddress(w.getAddress()))
                .toList();
    }
}

