package com.program.typingpractice.service.user;

import com.program.typingpractice.domain.user.User;
import com.program.typingpractice.dto.user.response.LoginResponseDto;
import com.program.typingpractice.dto.user.request.RegisterAdminRequestDto;
import com.program.typingpractice.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
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
		if(userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
			throw new IllegalArgumentException("Username is already in use");
		}

		if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
			throw new IllegalArgumentException("Email is already in use");
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
			throw new IllegalArgumentException("Admin token is incorrect");
		}

		if(userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
			throw new IllegalArgumentException("Username is already in use");
		}

		if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
			throw new IllegalArgumentException("Email is already in use");
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
		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Email is already in use"));

		if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Wrong password");
		}

		session.setAttribute("user", user);
		return new LoginResponseDto(user.getEmail(), user.getUsername(), user.getRoles());
	}

	@Transactional
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
