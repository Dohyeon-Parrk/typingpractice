package com.program.typingpractice.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

	@Email(message = "올바른 이메일 형식을 입력해 주세요.")
	@NotNull(message = "이메일은 필수 입력 사항입니다.")
	private String email;

	@NotNull(message = "비밀번호는 필수 입력 사항입니다.")
	private String password;
}
