package com.kernel360.washinfo.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wash_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WashInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wash_info_id_gen")
    @SequenceGenerator(name = "wash_info_id_gen", sequenceName = "wash_info_wash_no_seq", allocationSize = 50)
    @Column(name = "wash_no", nullable = false)
    private Long washNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    private Integer washCount;
    private Integer monthlyExpense;
    private Integer interest;

    private WashInfo(Integer washCount, Integer monthlyExpense, Integer interest) {
        this.washCount = washCount;
        this.monthlyExpense = monthlyExpense;
        this.interest = interest;
    }


    public static WashInfo of(Integer washCount, Integer monthlyExpense, Integer interest) {
        return new WashInfo(washCount, monthlyExpense, interest);
    }

    public void settingMember(Member member) {
        this.member = member;
    }

    public void updateWashInfo(Integer washCount, Integer monthlyExpense, Integer interest) {
        this.washCount = washCount;
        this.monthlyExpense = monthlyExpense;
        this.interest = interest;
    }
}