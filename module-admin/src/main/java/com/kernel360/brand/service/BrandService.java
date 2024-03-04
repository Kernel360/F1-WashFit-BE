package com.kernel360.brand.service;

import com.kernel360.brand.dto.BrandDto;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface BrandService {

    List<BrandDto> findAllBrand();

    BrandDto findBrandByBrandId(Long brandNo);

    BrandDto findBrandByBrandName(String brandName);

    void createBrand(BrandDto brandDto);

    void deleteBrand(final Long brandNo);

    void updateBrand(BrandDto brandDto);

    void updateBrandDescription(final Long brandNo, final String description);

    void updateBrandName(final Long brandNo, final String brandName);

    void updateBrandCompanyName(final Long brandNo, final String companyName);

    void updateBrandNationName(final Long brandNo, final String nationName);
}
