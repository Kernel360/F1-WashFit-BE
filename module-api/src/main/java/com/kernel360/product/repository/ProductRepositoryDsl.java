package com.kernel360.product.repository;

import com.kernel360.product.dto.ProductResponse;
import com.kernel360.product.dto.ProductSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryDsl {
    Page<ProductResponse> findAllByCondition(ProductSearchDto condition, Pageable pageable);

}
