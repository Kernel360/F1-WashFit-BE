package com.kernel360.modulebatch.product.job.infra;

import com.kernel360.brand.entity.Brand;
import com.kernel360.brand.repository.BrandRepository;
import com.kernel360.ecolife.entity.ConcernedProduct;
import com.kernel360.ecolife.repository.ConcernedProductRepository;
import com.kernel360.modulebatch.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.enumset.SafetyStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kernel360.product.repository.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class ConcernedProductToProductListItemProcessor implements ItemProcessor<Brand, List<Product>> {

    private final ConcernedProductRepository concernedProductRepository;

    private final ProductRepositoryJpa productRepositoryJpa;

    private final BrandRepository brandRepository;

    @Override
    public List<Product> process(Brand brand) {
        List<ConcernedProduct> concernedProductList = concernedProductRepository
                .findByBrandNameAndCompanyName(
                        "%" + brand.getBrandName().replace(" ", "%") + "%",
                        "%" + brand.getCompanyName().replace(" ", "%") + "%");

        List<ProductDto> productDtoList = concernedProductList.stream()
                                                              .filter(cp -> cp.getInspectedOrganization() != null)
                                                              .map(cp -> ProductDto.of(cp.getProductName(),
                                                                      null,
                                                                      null,
                                                                      "취하".equals(cp.getProductType())
                                                                              ? SafetyStatus.DANGER
                                                                              : SafetyStatus.CONCERN,
                                                                      0,
                                                                      cp.getCompanyName(),
                                                                      cp.getReportNumber(),
                                                                      cp.getProductType(),
                                                                      LocalDate.from(cp.getIssuedDate()),
                                                                      cp.getSafetyInspectionStandard(),
                                                                      cp.getUpperItem(),
                                                                      cp.getItem(),
                                                                      null,
                                                                      null,
                                                                      null,
                                                                      null, null, null, null,
                                                                      null, null, null, null,
                                                                      cp.getManufacture(), null,
                                                                      cp.getManufactureNation(), null))
                                                              .toList();

        return convertToProductList(productDtoList);
    }

    private List<Product> convertToProductList(List<ProductDto> productDtoList) {
        List<Product> productList = new ArrayList<>();

        for (ProductDto productDto : productDtoList) {
            Optional<Product> foundProduct = productRepositoryJpa.findProductByProductNameAndCompanyName(
                    productDto.productName(), "%" + getCompanyNameWithoutSlash(productDto.companyName()) + "%");

            boolean foundInList = productList.stream().anyMatch(
                    product -> product.getProductName().equals(productDto.productName()) &&
                            product.getCompanyName().equals(productDto.companyName()));

            if (foundInList) { // 현재 처리할 데이터에 이미 추가가 되어 있다면 무시
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

    public static String getCompanyNameWithoutSlash(String companyName) {
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
