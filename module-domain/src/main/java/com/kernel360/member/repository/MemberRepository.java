package com.kernel360.member.repository;

import com.kernel360.member.entity.Member;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Id> {

    Member findOneByIdAndPassword(String id, String password);

    Member findOneById(String id);

    Member findOneByEmail(String email);


}
