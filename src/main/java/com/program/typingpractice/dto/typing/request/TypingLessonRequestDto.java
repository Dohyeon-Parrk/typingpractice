package com.program.typingpractice.dto.typing.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypingLessonRequestDto {
    private String title;
    private String language;
    private String difficulty;
    private String content;
}
