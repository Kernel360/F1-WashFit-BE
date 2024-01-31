package com.kernel360.modulebatch.product.job.infra;

import com.kernel360.brand.entity.Brand;
import com.kernel360.brand.repository.BrandRepository;
import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.ecolife.repository.ReportedProductRepository;
import com.kernel360.modulebatch.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;
import com.kernel360.product.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class ReportedProductToProductListProcessor implements ItemProcessor<Brand, List<Product>> {

    private final ReportedProductRepository reportedProductRepository;

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    @PostConstruct
    public void postConstruct() {
        log.info("ReportedProductToProductListProcessor postConstruct with productRepository: "
                + productRepository.getClass());
    }

    @Override
    public List<Product> process(Brand brand) throws Exception {
        List<ReportedProduct> reportedProductList = reportedProductRepository
                .findByBrandNameAndCompanyName(
                        "%" + brand.getBrandName().replaceAll(" ", "%") + "%",
                        "%" + brand.getCompanyName().replaceAll(" ", "%") + "%"
                );

        List<ProductDto> productDtoList = reportedProductList.stream()
                                                             .filter(rp -> rp.getInspectedOrganization() != null)
                                                             .map(rp -> ProductDto.of(rp.getProductName(),
                                                                     null,
                                                                     null,
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
                                                                     rp.getManufactureNation()
                                                                     , null))
                                                             .collect(Collectors.toList());

        return convertToProductList(productDtoList);
    }


    /**
     * entity 로 변환할 dto 에 해당하는 제품이 있는지 검사하기 위해 product 테이블에서 제품을 검색한 이후, 없으면 새 제품 추가 있으면 업데이트
     */
    private List<Product> convertToProductList(List<ProductDto> productDtoList) {
        List<Product> productList = new ArrayList<>();

        for (ProductDto productDto : productDtoList) {
            Optional<Product> foundProduct = productRepository.findProductByProductNameAndCompanyName(
                    productDto.productName(), "%" + getCompanyNameWithoutSlash(productDto.companyName()) + "%");

            boolean foundInList = productList.stream().anyMatch(
                    product -> product.getProductName().equals(productDto.productName()) &&
                            product.getCompanyName().equals(productDto.companyName()));

            if (foundInList) { // 현재 처리할 데이터에 이미 추가가 되어 있다면
                continue;
            }

            if (foundProduct.isEmpty()) {
                Product newProduct = generateNewProduct(productDto);
                productList.add(newProduct);
                continue;
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
            }
        }

        return productList;
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

    private String getNation(ProductDto productDto) {
        if (productDto.manufactureType().equals("수입")) {
            if (!productDto.manufactureNation().isBlank()) {
                return productDto.manufactureNation();
            }
            List<Brand> brandList = brandRepository.findByCompanyName(productDto.companyName());
            for (Brand b : brandList) {
                if (productDto.productName().contains(b.getBrandName())) {
                    return b.getNationName();
                }
            }
        }
        return "대한민국";
    }
}
