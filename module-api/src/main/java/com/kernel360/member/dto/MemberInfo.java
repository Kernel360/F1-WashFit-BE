package com.kernel360.member.dto;

public record MemberInfo( String id,
                          String password
) {
    static MemberInfo of(
            String id,
            String password
    ) {
        return new MemberInfo(id, password);
    }
}