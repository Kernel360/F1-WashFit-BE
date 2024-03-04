package com.kernel360.brand.service;

import com.kernel360.brand.code.BrandErrorCode;
import com.kernel360.brand.dto.BrandDto;
import com.kernel360.brand.entity.Brand;
import com.kernel360.brand.repository.BrandRepository;
import com.kernel360.exception.BusinessException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<BrandDto> findAllBrand() {
        List<Brand> brandList = brandRepository.findAll();
        if (brandList.isEmpty()) {
            throw new BusinessException(BrandErrorCode.FAILED_CANNOT_FOUND_ANY_BRAND);
        }

        return brandList.stream()
                        .map(BrandDto::fromEntity)
                        .collect(Collectors.toList());
    }

    @Override
    public BrandDto findBrandByBrandId(Long brandNo) {
        Optional<Brand> brand = brandRepository.findBrandByBrandNo(brandNo);
        if (brand.isEmpty()) {
            throw new BusinessException(BrandErrorCode.FAILED_CANNOT_FOUND_EXACT_BRAND);
        }

        return BrandDto.fromEntity(brand.get());
    }

    @Override
    public BrandDto findBrandByBrandName(String brandName) {
        Brand brand = brandRepository.findBrandByBrandName(brandName)
                                     .orElseThrow(() -> new BusinessException(
                                             BrandErrorCode.FAILED_CANNOT_FOUND_EXACT_BRAND));

        return BrandDto.fromEntity(brand);
    }

    @Override
    @Transactional
    public void createBrand(BrandDto brandDto) {
        Optional<Brand> brand = brandRepository.findBrandByBrandName(brandDto.brandName());
        if (brand.isPresent()) {
            throw new BusinessException(BrandErrorCode.FAILED_ALREADY_EXISTS_BRAND);
        }

        brandRepository.save(Brand.toEntity(brandDto.brandName(), brandDto.companyName(), brandDto.description(), brandDto.nationName()));
    }

    @Override
    @Transactional
    public void deleteBrand(Long brandNo) {
        Optional<Brand> brand = brandRepository.findBrandByBrandNo(brandNo);
        if (brand.isEmpty()) {
            throw new BusinessException(BrandErrorCode.FAILED_CANNOT_FOUND_EXACT_BRAND);
        }

        brandRepository.delete(brand.get());
    }

    @Override
    @Transactional
    public void updateBrand(BrandDto brandDto) {
        Optional<Brand> brand = brandRepository.findBrandByBrandNo(brandDto.brandNo());
        if (brand.isEmpty()) {
            throw new BusinessException(BrandErrorCode.FAILED_CANNOT_FOUND_EXACT_BRAND);
        }

        brand.get()
             .updateAll(brandDto.brandName(), brandDto.companyName(), brandDto.description(), brandDto.nationName());
    }

    @Override
    @Transactional
    public void updateBrandDescription(Long brandNo,String description) {
        Optional<Brand> brand = brandRepository.findBrandByBrandNo(brandNo);
        if (brand.isEmpty()) {
            throw new BusinessException(BrandErrorCode.FAILED_CANNOT_FOUND_EXACT_BRAND);
        }

        brand.get().updateDescription(description);
    }

    @Override
    @Transactional
    public void updateBrandName(Long brandNo, String brandName) {
        Optional<Brand> brand = brandRepository.findBrandByBrandNo(brandNo);
        if (brand.isEmpty()) {
            throw new BusinessException(BrandErrorCode.FAILED_CANNOT_FOUND_EXACT_BRAND);
        }

        brand.get().updateBrandName(brandName);
    }

    @Override
    @Transactional
    public void updateBrandCompanyName(Long brandNo, String companyName) {
        Optional<Brand> brand = brandRepository.findBrandByBrandNo(brandNo);
        if (brand.isEmpty()) {
            throw new BusinessException(BrandErrorCode.FAILED_CANNOT_FOUND_EXACT_BRAND);
        }

        brand.get().updateBrandCompanyName(companyName);
    }

    @Override
    @Transactional
    public void updateBrandNationName(Long brandNo, String nationName) {
        Optional<Brand> brand = brandRepository.findBrandByBrandNo(brandNo);
        if (brand.isEmpty()) {
            throw new BusinessException(BrandErrorCode.FAILED_CANNOT_FOUND_EXACT_BRAND);
        }

        brand.get().updateBrandNationName(nationName);
    }


}
