package com.kernel360.likes.repository;

import com.kernel360.likes.dto.LikeSearchDto;
import com.kernel360.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeRepositoryDsl {
    Page<ProductResponse> findAllByCondition(LikeSearchDto condition, Pageable pageable);
}
