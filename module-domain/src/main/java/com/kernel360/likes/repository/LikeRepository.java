package com.kernel360.likes.repository;

import com.kernel360.likes.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberIdAndProductNo(String memberId, Long productNo);
    Page<Like> findAllByMemberId(String memberId, Pageable pageable);

}
