package com.program.typingpractice.service.user;

import com.program.typingpractice.domain.user.User;
import com.program.typingpractice.dto.user.response.LoginResponseDto;
import com.program.typingpractice.dto.user.request.RegisterAdminRequestDto;
import com.program.typingpractice.global.CustomException;
import com.program.typingpractice.global.ErrorCode;
import com.program.typingpractice.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.program.typingpractice.dto.user.request.RegisterRequestDto;
import com.program.typingpractice.dto.user.request.LoginRequestDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

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
	public void register(RegisterRequestDto requestDto) {

		if(requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty()){
			throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
		}

		if(requestDto.getUsername() == null || requestDto.getUsername().trim().isEmpty()){
			throw new CustomException(ErrorCode.USERNAME_REQUIRED);
		}

		if(requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()){
			throw new CustomException(ErrorCode.PASSWORD_REQUIRED);
		}

		if(userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
			throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
		}

		if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
			throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
		}

		User user = User.builder()
				.email(requestDto.getEmail())
				.username(requestDto.getUsername())
				.password(passwordEncoder.encode(requestDto.getPassword()))
				.roles(Set.of("USER"))
				.build();

		userRepository.save(user);
	}

	@Transactional
	public void registerAdmin(RegisterAdminRequestDto requestDto) {

		if(!requestDto.getAdminToken().equals(adminToken)){
			throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
		}

		if(requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty()){
			throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
		}

		if(requestDto.getUsername() == null || requestDto.getUsername().trim().isEmpty()){
			throw new CustomException(ErrorCode.USERNAME_REQUIRED);
		}

		if(requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()){
			throw new CustomException(ErrorCode.PASSWORD_REQUIRED);
		}

		if(userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
			throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
		}

		if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
			throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
		}

		User user = User.builder()
				.email(requestDto.getEmail())
				.username(requestDto.getUsername())
				.password(passwordEncoder.encode(requestDto.getPassword()))
				.roles(Set.of("ADMIN"))
				.build();

		userRepository.save(user);
	}

	public LoginResponseDto login(LoginRequestDto requestDto, HttpSession session) {

		if(requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty()){
			throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
		}

		if(requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()){
			throw new CustomException(ErrorCode.PASSWORD_REQUIRED);
		}

		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.WRONG_PASSWORD);
		}

		session.setAttribute("user", user);
		return new LoginResponseDto(user.getEmail(), user.getUsername(), user.getRoles());
	}

	@Transactional
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
