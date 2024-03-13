package com.kernel360.bbs.repository;

import com.kernel360.bbs.entity.BBS;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BBSRepositoryJPA extends JpaRepository<BBS, Id> {
    BBS findOneByBbsNo(Long bbsNo);

    Page<BBS> findAllByUpperNo(Long upperNo, Pageable pageable);


    void deleteByBbsNo(Long bbsNo);
}
