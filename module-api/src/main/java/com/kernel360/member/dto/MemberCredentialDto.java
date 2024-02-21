package com.kernel360.member.dto;

public record MemberCredentialDto(String authToken,
                                  String email,
                                  String memberId,
                                  String password) {
    public static MemberCredentialDto of(String authToken, String email, String memberId, String password) {

        return new MemberCredentialDto(authToken, email, memberId, password);
    }
}
