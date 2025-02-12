package com.program.typingpractice.controller.typing;

import com.program.typingpractice.dto.typing.request.TypingLessonRequestDto;
import com.program.typingpractice.dto.typing.response.TypingLessonResponseDto;
import com.program.typingpractice.service.typing.TypingLessonService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class TypingLessonController {

    private final TypingLessonService typingLessonService;

    // 레슨 목록 조회 ( 일반 유저 )
    @GetMapping
    public ResponseEntity<TypingLessonResponseDto> getLessons(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String difficulty){
        TypingLessonResponseDto responseDto = typingLessonService.getLessons(language, difficulty);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    // 단건 조회 ( 일반 유저 )
    @GetMapping("/{lessonId}")
    public ResponseEntity<TypingLessonResponseDto> getLessonById(
            @PathVariable Long lessonId, HttpSession session){
        TypingLessonResponseDto responseDto = typingLessonService.getLessonById(lessonId, session);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    // 레슨 추가 (관리자)
    @PostMapping
    public ResponseEntity<TypingLessonResponseDto> createLesson(
            @Valid @RequestBody TypingLessonRequestDto requestDto,
            HttpSession session) {
        TypingLessonResponseDto responseDto = typingLessonService.createLesson(requestDto, session);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 레슨 수정 (관리자)
    @PutMapping("/{lessonId}")
    public ResponseEntity<TypingLessonResponseDto> updateLesson(
            @PathVariable Long lessonId,
            @Valid @RequestBody TypingLessonRequestDto requestDto,
            HttpSession session) {
        TypingLessonResponseDto responseDto = typingLessonService.updateLesson(lessonId, requestDto, session);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    // 레슨 삭제 (관리자)
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<TypingLessonResponseDto> deleteLesson(@PathVariable Long lessonId, HttpSession session) {
        typingLessonService.deleteLesson(lessonId, session);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
