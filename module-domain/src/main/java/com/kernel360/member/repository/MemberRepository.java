package com.kernel360.member.repository;

import com.kernel360.member.entity.Member;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberRepository extends JpaRepository<Member, Id> {

    Member findOneByIdAndPassword(String id, String password);

    Member findOneById(String id);

    Member findOneByEmail(String email);

    void deleteMemberById(String id);

    @Modifying
    @Query("update Member m set m.password = :password where m.id = :id")
    void updatePasswordById(@Param("id") String id, @Param("password") String password);

}
