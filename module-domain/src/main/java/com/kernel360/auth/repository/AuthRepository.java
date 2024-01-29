package com.kernel360.auth.repository;

import com.kernel360.auth.entity.Auth;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository <Auth, Id> {

    Auth findOneByMemberNo(Long memberNo);

    Auth findOneByJwtToken(String jwtToken);
}
