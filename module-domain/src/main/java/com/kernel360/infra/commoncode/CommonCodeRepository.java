package com.kernel360.infra.commoncode;

import com.kernel360.entity.commoncode.CommonCode;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Id> {
    List<CommonCode> findAllByUpperNameAndIsUsed(String codeName, boolean isUsed);
}
