package com.kernel360.member.repository;

import com.kernel360.member.entity.WithdrawMember;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawMemberRepository extends JpaRepository<WithdrawMember, Id> {
}
