package com.kernel360.carinfo.repository;

import com.kernel360.carinfo.entity.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {
}
