package com.program.typingpractice.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String message;
    private String email;
    private String username;
    private Set<String> roles;
}
