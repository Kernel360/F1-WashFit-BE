package com.kernel360.bbs.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bbs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BBS extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bbs_id_gen")
    @SequenceGenerator(name = "bbs_id_gen", sequenceName = "bbs_bbs_no_seq")
    private Long bbsNo;

    private Long upperNo;

    private String type;

    private String title;

    private String contents;

    private Boolean isVisible;

    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false, updatable = false)
    private Member member;

    private BBS(Long bbsNo, Long upperNo, String type, String title, String contents, Boolean isVisible, Long viewConut, Member member) {
        this.bbsNo = bbsNo;
        this.upperNo = upperNo;
        this.type = type;
        this.title = title;
        this.contents = contents;
        this.isVisible = isVisible;
        this.member = member;
        this.viewCount = viewConut;
    }

    public static BBS save(Long bbsNo, Long upperNo, String type, String title, String contents, Boolean isVisible, Long viewCount, Member member){

        return new BBS (bbsNo, upperNo, type, title, contents, isVisible, viewCount, member);
    }

}
