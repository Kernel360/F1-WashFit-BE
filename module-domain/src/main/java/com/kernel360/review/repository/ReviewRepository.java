package com.kernel360.review.repository;

import com.kernel360.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByProduct_ProductNoOrderByReviewNoDesc(Long productNo, Pageable pageable);

    Review findByReviewNo(Long reviewNo);
}
