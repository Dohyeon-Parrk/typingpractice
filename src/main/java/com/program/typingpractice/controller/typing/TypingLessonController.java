package com.program.typingpractice.controller.typing;

import com.program.typingpractice.dto.typing.TypingLessonResponseDto;
import com.program.typingpractice.service.typing.TypingLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class TypingLessonController {

    private final TypingLessonService typingLessonService;

    @GetMapping
    public ResponseEntity<List<TypingLessonResponseDto>> getLessons(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String difficulty){
        List<TypingLessonResponseDto> lessons = typingLessonService.getLessons(language, difficulty);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lessons);
    }

}
