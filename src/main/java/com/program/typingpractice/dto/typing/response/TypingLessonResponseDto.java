package com.program.typingpractice.dto.typing.response;

import com.program.typingpractice.domain.typing.TypingLesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class TypingLessonResponseDto {
    private String message;
    private List<LessonDetail> lessons;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class LessonDetail {
        private Long id;
        private String title;
        private String language;
        private String difficulty;
        private String content;
    }

    public static TypingLessonResponseDto of(String message, List<TypingLesson> lessonList){
        return TypingLessonResponseDto.builder()
                .message(message)
                .lessons(lessonList.stream()
                        .map(lesson -> LessonDetail.builder()
                                .id(lesson.getId())
                                .title(lesson.getTitle())
                                .language(lesson.getLanguage())
                                .difficulty(lesson.getDifficulty())
                                .content(lesson.getContent())
                                .build())
                        .collect(Collectors.toList()))
                .build();

    }
}
