package com.program.typingpractice.service.user;

import com.program.typingpractice.domain.user.User;
import com.program.typingpractice.dto.user.request.LoginRequestDto;
import com.program.typingpractice.dto.user.request.RegisterAdminRequestDto;
import com.program.typingpractice.dto.user.request.RegisterRequestDto;
import com.program.typingpractice.dto.user.response.AuthResponseDto;
import com.program.typingpractice.global.CustomException;
import com.program.typingpractice.global.ErrorCode;
import com.program.typingpractice.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    // 관리자 계정 토큰
    @Value("${admin.token.secret.key}")
    private String adminToken;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponseDto register(RegisterRequestDto requestDto) {

        if (requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        if (requestDto.getUsername() == null || requestDto.getUsername().trim().isEmpty()) {
            throw new CustomException(ErrorCode.USERNAME_REQUIRED);
        }

        if (requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()) {
            throw new CustomException(ErrorCode.PASSWORD_REQUIRED);
        }

        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(Set.of("USER"))
                .build();

        userRepository.save(user);

        return new AuthResponseDto(
                "회원가입 완료", user.getEmail(), user.getUsername(), user.getRoles());
    }

    @Transactional
    public AuthResponseDto registerAdmin(RegisterAdminRequestDto requestDto) {

        if (!requestDto.getAdminToken().equals(adminToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        if (requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        if (requestDto.getUsername() == null || requestDto.getUsername().trim().isEmpty()) {
            throw new CustomException(ErrorCode.USERNAME_REQUIRED);
        }

        if (requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()) {
            throw new CustomException(ErrorCode.PASSWORD_REQUIRED);
        }

        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User admin = User.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(Set.of("ADMIN"))
                .build();

        userRepository.save(admin);

        return new AuthResponseDto(
                "관리자 등록 완료", admin.getEmail(), admin.getUsername(), admin.getRoles());
    }

    public AuthResponseDto login(LoginRequestDto requestDto, HttpSession session) {

        if (requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        if (requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()) {
            throw new CustomException(ErrorCode.PASSWORD_REQUIRED);
        }

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        session.setAttribute("user", user);
        return new AuthResponseDto(
                "로그인 성공", user.getEmail(), user.getUsername(), user.getRoles());
    }

    public void logout(HttpSession session) {
        if (session == null || session.getAttribute("user") == null) {
            throw new CustomException(ErrorCode.SESSION_NOT_FOUND);
        }
            session.invalidate();
    }

    public ResponseEntity<AuthResponseDto> checkSession(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new CustomException(ErrorCode.SESSION_NOT_FOUND);
        }

        AuthResponseDto responseDto = new AuthResponseDto("세션 계정", user.getEmail(), user.getUsername(), user.getRoles());
        return ResponseEntity.ok(responseDto);
    }
}
