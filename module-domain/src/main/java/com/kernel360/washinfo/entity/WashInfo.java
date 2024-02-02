package com.kernel360.washinfo.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wash_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WashInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wash_info_id_gen")
    @SequenceGenerator(name = "wash_info_id_gen", sequenceName = "wash_info_wash_no_seq", allocationSize = 50)
    @Column(name = "wash_no", nullable = false)
    private Integer washNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "wash_count")
    private Integer washCount;

    @Column(name = "monthly_expense")
    private Integer monthlyExpense;

    @Column(name = "interest")
    private Integer interest;

    private WashInfo(Integer washNo, Integer washCount, Integer monthlyExpense, Integer interest) {
        this.washNo = washNo;
        this.washCount = washCount;
        this.monthlyExpense = monthlyExpense;
        this.interest = interest;
    }


    public static WashInfo of(Integer washNo, Integer washCount, Integer monthlyExpense, Integer interest) {
        return new WashInfo(washNo, washCount, monthlyExpense, interest);
    }
}