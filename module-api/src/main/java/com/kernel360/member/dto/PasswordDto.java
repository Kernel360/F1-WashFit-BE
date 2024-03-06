package com.kernel360.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordDto(
        @NotBlank(message = "비밀번호는 비어 있을수 없습니다.")
        @Size(min = 8, max = 16, message = "비밀번호는 8~16글자입니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,20}$", message = "8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.")
//        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).{8,16}$", message = "8~16자의 영문자(대/소문자 구분 없음), 숫자, 특수문자를 사용해 주세요.")
        String password
) {
}
