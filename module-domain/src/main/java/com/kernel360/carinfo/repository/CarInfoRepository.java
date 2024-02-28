package com.kernel360.carinfo.repository;

import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {

    CarInfo findCarInfoByMember(Member member);
}
