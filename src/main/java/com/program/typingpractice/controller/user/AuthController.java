package com.program.typingpractice.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.program.typingpractice.dto.user.RegisterRequestDto;
import com.program.typingpractice.dto.user.LoginRequestDto;
import com.program.typingpractice.service.user.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public String register(@RequestBody RegisterRequestDto requestDto) {
		return authService.register(requestDto);
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequestDto requestDto, HttpSession session){
		return authService.login(requestDto, session);
	}

	@PostMapping("/logout")
	public String logout(HttpSession session){
		return authService.logout(session);
	}
}
