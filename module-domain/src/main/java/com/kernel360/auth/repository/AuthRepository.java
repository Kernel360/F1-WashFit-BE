package com.kernel360.auth.repository;

import com.kernel360.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository <Auth, Long> {

    Auth findOneByMemberNo(Long memberNo);

}
