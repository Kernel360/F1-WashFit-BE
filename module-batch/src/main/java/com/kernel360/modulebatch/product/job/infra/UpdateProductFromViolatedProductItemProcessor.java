package com.kernel360.modulebatch.product.job.infra;

import com.kernel360.modulebatch.product.dto.ProductJoinDto;
import com.kernel360.product.entity.Product;

import java.util.Optional;

import com.kernel360.product.repository.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateProductFromViolatedProductItemProcessor implements ItemProcessor<ProductJoinDto, Product> {

    private final ProductRepositoryJpa productRepositoryJpa;

    @Override
    public Product process(ProductJoinDto item) throws Exception {
        String productName = item.productName();
        String companyName = item.companyName();

        Optional<Product> foundProduct = productRepositoryJpa.findProductByProductNameAndCompanyName(productName,
                companyName);

        if (foundProduct.isPresent()) {
            foundProduct.get().updateViolatedInfo(
                    item.actionedDate() + "/" + item.violatedCn() + "/" + item.actionCn() + "/" + item.etcInfo());
            return foundProduct.get();
        }

        return null;
    }
}
