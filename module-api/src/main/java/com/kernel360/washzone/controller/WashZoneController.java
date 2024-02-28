package com.kernel360.washzone.controller;

import com.kernel360.response.ApiResponse;
import com.kernel360.washzone.code.WashZoneBusinessCode;
import com.kernel360.washzone.dto.KakaoMapDto;
import com.kernel360.washzone.dto.WashZoneDto;
import com.kernel360.washzone.service.WashZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/washzones")
@RequiredArgsConstructor
public class WashZoneController {

    private final WashZoneService washZoneService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<WashZoneDto>>> getWashZones(@ModelAttribute KakaoMapDto kakaoMapDto) {
        List<WashZoneDto> washZones = washZoneService.getWashZones(kakaoMapDto);

        return ApiResponse.toResponseEntity(WashZoneBusinessCode.SUCCESS_REQUEST_WASH_ZONE_INFO, washZones);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<WashZoneDto>>> getWashZoneByKeyword(
            @RequestParam("keyword") String keyword, Pageable pageable){
        Page<WashZoneDto> washZoneDtos = washZoneService.getWashZonesByKeyword(keyword, pageable);

        return ApiResponse.toResponseEntity(WashZoneBusinessCode.SUCCESS_REQUEST_WASH_ZONE_INFO, washZoneDtos);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WashZoneDto>> registerWashZone(@RequestBody WashZoneDto washZoneDto){
        WashZoneDto savedWashZoneDto = WashZoneDto.from(washZoneService.save(washZoneDto));

        return ApiResponse.toResponseEntity(WashZoneBusinessCode.SUCCESS_SAVED_WASH_ZONE_INFO, savedWashZoneDto);
    }

}