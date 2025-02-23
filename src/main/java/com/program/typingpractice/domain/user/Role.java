package com.program.typingpractice.domain.user;

import com.program.typingpractice.global.CustomException;
import com.program.typingpractice.global.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN,
    ROLE_USER;

    public static Role fromString(String role) {
        return switch (role.toUpperCase()) {
            case "ROLE_ADMIN" -> ROLE_ADMIN;
            case "ROLE_USER" -> ROLE_USER;
            default -> throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
        };
    }
}
