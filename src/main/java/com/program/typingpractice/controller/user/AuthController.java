package com.program.typingpractice.controller.user;

import com.program.typingpractice.dto.user.request.LoginRequestDto;
import com.program.typingpractice.dto.user.request.RegisterAdminRequestDto;
import com.program.typingpractice.dto.user.request.RegisterRequestDto;
import com.program.typingpractice.dto.user.response.AuthResponseDto;
import com.program.typingpractice.service.user.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto requestDto) {
		AuthResponseDto responseDto = authService.register(requestDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(responseDto);
	}

	@PostMapping("/register/admin")
	public ResponseEntity<AuthResponseDto> registerAdmin(@Valid @RequestBody RegisterAdminRequestDto requestDto) {
		AuthResponseDto responseDto = authService.registerAdmin(requestDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(responseDto);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto, HttpSession session){
		AuthResponseDto responseDto = authService.login(requestDto, session);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseDto);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession session){
		authService.logout(session);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@GetMapping("/session")
	public ResponseEntity<AuthResponseDto> checkSession(HttpSession session){
		return authService.checkSession(session);
	}
}
