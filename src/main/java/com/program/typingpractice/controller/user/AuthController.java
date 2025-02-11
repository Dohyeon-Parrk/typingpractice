package com.program.typingpractice.controller.user;

import com.program.typingpractice.domain.user.User;
import com.program.typingpractice.dto.user.response.LoginResponseDto;
import com.program.typingpractice.dto.user.request.RegisterAdminRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.program.typingpractice.dto.user.request.RegisterRequestDto;
import com.program.typingpractice.dto.user.request.LoginRequestDto;
import com.program.typingpractice.service.user.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto requestDto) {
		authService.register(requestDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("회원가입 완료");
	}

	@PostMapping("/register/admin")
	public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterAdminRequestDto requestDto) {
		authService.registerAdmin(requestDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("관리자 등록 완료");
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto, HttpSession session){
		LoginResponseDto responseDto = authService.login(requestDto, session);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseDto);
	}

	@GetMapping("/logout/{userId}")
	public ResponseEntity<Void> logout(HttpSession session){
		authService.logout(session);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@GetMapping("/me")
	public Optional<User> getAuthenticatedUser(HttpSession session){
		return Optional.ofNullable((User) session.getAttribute("user"));
	}
}
