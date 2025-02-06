package com.program.typingpractice.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAdminRequestDto {

    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    @NotNull(message = "이메일은 필수 입력 사항입니다.")
    private String email;

    @NotNull(message = "유저네임은 필수 입력 사항입니다.")
    private String username;

    @NotNull(message = "비밀번호는 필수 입력 사항입니다.")
    private String password;

    @NotNull(message = "관리자 인증 코드를 입력해 주세요.")
    private String adminToken;
}
