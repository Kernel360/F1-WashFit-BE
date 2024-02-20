package com.kernel360.review.repository;

import com.kernel360.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProduct_ProductNo(Long productNo);

    Review findByReviewNo(Long reviewNo);
}
