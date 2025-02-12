package com.program.typingpractice.service.typing;

import com.program.typingpractice.dto.typing.TypingLessonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypingLessonService {
    public List<TypingLessonResponseDto> getLessons(String language, String difficulty) {
        return null;
    }
}
