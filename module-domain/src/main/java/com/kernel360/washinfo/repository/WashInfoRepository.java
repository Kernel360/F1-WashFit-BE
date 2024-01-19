package com.kernel360.washinfo.repository;

import com.kernel360.washinfo.entity.WashInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WashInfoRepository extends JpaRepository<WashInfo, Long> {
}
