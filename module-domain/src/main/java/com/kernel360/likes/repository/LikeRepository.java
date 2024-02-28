package com.kernel360.likes.repository;

import com.kernel360.likes.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberNoAndProductNo(Long memberNo, Long productNo);

    Page<Like> findAllByMemberNo(Long memberNo, Pageable pageable);

    @Query(value = "SELECT l.productNo, COUNT(l) AS likeCount FROM Like l GROUP BY l.productNo ORDER BY likeCount DESC")
    Page<Object[]> findTop20ByProductNoOrderByLikeCountDesc(Pageable pageable);
}
