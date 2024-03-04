package com.kernel360.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberCredentialDto(@JsonProperty("token") String authToken,
                                  @JsonProperty("email") String email,
                                  @JsonProperty("id") String memberId,
                                  @JsonProperty("password") String password) {
    public static MemberCredentialDto of(String authToken, String email, String memberId, String password) {

        return new MemberCredentialDto(authToken, email, memberId, password);
    }
}
