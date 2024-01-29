package com.kernel360.main.service;

import com.kernel360.main.dto.BannerDto;
import org.springframework.stereotype.Service;

@Service

public class MainService {

    public BannerDto getSampleBanner() {
        return BannerDto.of(1L, "classpath:static/bannerSample.png", "Banner Image");
    }

}
