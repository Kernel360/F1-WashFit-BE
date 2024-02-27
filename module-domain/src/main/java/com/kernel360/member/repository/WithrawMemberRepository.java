package com.kernel360.member.repository;

import com.kernel360.member.entity.WithrawMember;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithrawMemberRepository extends JpaRepository<WithrawMember, Id> {
}
