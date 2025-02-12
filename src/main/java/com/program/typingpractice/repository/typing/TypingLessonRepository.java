package com.program.typingpractice.repository.typing;

import com.program.typingpractice.domain.typing.TypingLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypingLessonRepository extends JpaRepository<TypingLesson, Long> {

    List<TypingLesson> findByLanguageAndDifficulty(String language, String difficulty);

    List<TypingLesson> findByLanguage(String language);

    List<TypingLesson> findByDifficulty(String difficulty);

    Optional<Object> findByTitle(String title);
}
