package com.kernel360.commoncode.repository;

import com.kernel360.commoncode.entity.CommonCode;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Id> {
    List<CommonCode> findAllByUpperNameAndIsUsed(String codeName, boolean isUsed);
}
