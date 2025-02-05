package com.program.typingpractice.service.user;

import com.program.typingpractice.domain.user.User;
import com.program.typingpractice.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.program.typingpractice.dto.user.RegisterRequestDto;
import com.program.typingpractice.dto.user.LoginRequestDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public String register(RegisterRequestDto requestDto) {
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
				.roles(Set.of("ROLE_USER"))
				.build();

		userRepository.save(user);
		return "회원가입 성공";
	}

	public String login(LoginRequestDto requestDto, HttpSession session) {
		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Email is already in use"));

		if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Wrong password");
		}

		session.setAttribute("user", user);
		return "로그인 성공";
	}

	@Transactional
	public String logout(HttpSession session) {
		session.invalidate();
		return "로그아웃 성공";
	}
}
