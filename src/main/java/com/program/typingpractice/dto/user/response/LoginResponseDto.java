package com.program.typingpractice.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String email;
    private String password;
    private Set<String> roles;
}
