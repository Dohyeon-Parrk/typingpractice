package com.program.typingpractice.service.user;

import org.springframework.stereotype.Service;

import com.program.typingpractice.dto.user.RegisterRequestDto;
import com.program.typingpractice.dto.user.LoginRequestDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	public String register(RegisterRequestDto requestDto) {
		return null;
	}

	public String login(LoginRequestDto requestDto, HttpSession session) {
		return null;
	}

	public String logout(HttpSession session) {
		return null;
	}
}
