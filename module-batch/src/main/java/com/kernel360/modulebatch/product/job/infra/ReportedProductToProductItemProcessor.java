package com.kernel360.modulebatch.product.job.infra;

import com.kernel360.brand.entity.Brand;
import com.kernel360.brand.repository.BrandRepository;
import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.modulebatch.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.kernel360.product.repository.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
@ComponentScan("com.kernel360.product")
@ComponentScan("com.kernel360.brand")
@RequiredArgsConstructor
public class ReportedProductToProductItemProcessor implements ItemProcessor<ReportedProduct, Product> {
    private final ProductRepositoryJpa productRepositoryJpa;

    private final BrandRepository brandRepository;

    @Override
    public Product process(ReportedProduct rp) throws Exception {
        if (rp.getInspectedOrganization() == null) {
            return null;
        }

        ProductDto productDto = ProductDto.of(rp.getProductName(), null, null,
                "취하".equals(rp.getRenewType())
                        ? SafetyStatus.DANGER
                        : SafetyStatus.CONCERN,
                0,
                rp.getCompanyName(),
                rp.getSafetyReportNumber(),
                rp.getProductType(),
                LocalDate.from(rp.getIssuedDate()),
                rp.getSafeStandard(), rp.getUpperItem(),
                rp.getItem(),
                rp.getProductPropose(), rp.getWeightAndBulk(),
                rp.getUseMethod(),
                rp.getUsageAttentionReport(),
                rp.getFirstAid(), rp.getMainSubstance(),
                rp.getAllergicSubstance(),
                rp.getOtherSubstance(), rp.getPreservative(),
                rp.getSurfactant(),
                rp.getFluorescentWhiteningAgent(),
                rp.getManufacture(), rp.getManufactureMethod(),
                rp.getManufactureNation(), null);

        Optional<Product> foundProduct = productRepositoryJpa
                .findProductByProductNameAndCompanyName(productDto.productName(),
                        addWildCardToCompanyName(getCompanyNameWithoutSlash(productDto.companyName())));

        if (foundProduct.isEmpty()) {
            return generateNewProduct(productDto);
        }

        if (productDto.issuedDate().isAfter(foundProduct.get().getIssuedDate())) { // 저장된 데이터보다 최신의 제품 데이터라면
            foundProduct.get().updateDetail(productDto.barcode(),
                    productDto.imageSource(),
                    productDto.reportNumber(),
                    String.valueOf(productDto.safetyStatus()),
                    productDto.issuedDate(),
                    productDto.safetyInspectionStandard(),
                    productDto.upperItem(),
                    productDto.item(),
                    productDto.propose(),
                    productDto.weight(),
                    productDto.usage(),
                    productDto.usagePrecaution(),
                    productDto.firstAid(),
                    productDto.mainSubstance(),
                    productDto.allergicSubstance(),
                    productDto.otherSubstance(),
                    productDto.preservative(),
                    productDto.surfactant(),
                    productDto.fluorescentWhitening(),
                    productDto.manufactureType(),
                    productDto.manufactureMethod(),
                    getNation(productDto),
                    productDto.violationInfo());

            return foundProduct.get();
        }

        return null;
    }

    private Product generateNewProduct(ProductDto productDto) {

        return Product.of(productDto.productName(), productDto.barcode(),
                productDto.imageSource(), productDto.reportNumber(), String.valueOf(productDto.safetyStatus()),
                productDto.viewCount(), getCompanyNameWithoutSlash(productDto.companyName()), productDto.productType(),
                productDto.issuedDate(), productDto.safetyInspectionStandard(),
                productDto.upperItem(),
                productDto.item(), productDto.propose(), productDto.weight(), productDto.usage(),
                productDto.usagePrecaution(), productDto.firstAid(), productDto.mainSubstance(),
                productDto.allergicSubstance(), productDto.otherSubstance(), productDto.preservative(),
                productDto.surfactant(), productDto.fluorescentWhitening(), productDto.manufactureType(),
                productDto.manufactureMethod(), getNation(productDto), productDto.violationInfo());
    }

    public String getCompanyNameWithoutSlash(String companyName) {
        int index = companyName.indexOf("/");
        if (index != -1) {
            return companyName.substring(0, index);
        }
        return companyName;
    }

    public String addWildCardToCompanyName(String companyName) {
        return companyName.replaceAll(" ", "%");
    }

    private String getNation(ProductDto productDto) {
        if (productDto.manufactureType().equals("수입")) {
            if (!productDto.manufactureNation().isBlank()) {
                return productDto.manufactureNation();
            }
            String targetCompany = addWildCardToCompanyName(getCompanyNameWithoutSlash(productDto.companyName()));
            List<Brand> brandList = brandRepository.findByCompanyName(targetCompany);
            for (Brand b : brandList) {
                if (productDto.productName().contains(b.getBrandName())) {
                    return b.getNationName();
                }
            }
            if (!brandList.isEmpty()) {
                return brandList.get(0).getNationName();
            }
        }
        return "대한민국";
    }

}
