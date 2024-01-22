package com.kernel360.review.repository;

import com.kernel360.review.entity.Review;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Id> {
}
