package com.program.typingpractice.service.typing;

import com.program.typingpractice.domain.typing.TypingLesson;
import com.program.typingpractice.domain.user.User;
import com.program.typingpractice.dto.typing.request.TypingLessonRequestDto;
import com.program.typingpractice.dto.typing.response.TypingLessonResponseDto;
import com.program.typingpractice.global.CustomException;
import com.program.typingpractice.global.ErrorCode;
import com.program.typingpractice.repository.typing.TypingLessonRepository;
import com.program.typingpractice.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypingLessonService {

    private final TypingLessonRepository typingLessonRepository;
    private final UserRepository userRepository;

    // 레슨 목록 조회 (일반 유저)
    public TypingLessonResponseDto getLessons(String language, String difficulty) {
        List<TypingLesson> lessons;

        if (language != null && difficulty != null) {
            lessons = typingLessonRepository.findByLanguageAndDifficulty(language, difficulty);
        }else if(language != null) {
            lessons = typingLessonRepository.findByLanguage(language);
        }else if(difficulty != null) {
            lessons = typingLessonRepository.findByDifficulty(difficulty);
        } else {
            lessons = typingLessonRepository.findAll();
        }

        return TypingLessonResponseDto.of("레슨 목록 조회 성공", lessons);
    }

    // 단건 조회 ( 일반 유저 )
    public TypingLessonResponseDto getLessonById(Long lessonId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            throw new CustomException(ErrorCode.ERR_LOGIN_REQUIRED);
        }

        TypingLesson lesson = typingLessonRepository.findById(lessonId)
                .orElseThrow(() -> new CustomException(ErrorCode.LESSON_NOT_FOUND));

        return TypingLessonResponseDto.of("레슨 조회 성공", List.of(lesson));
    }

    // 레슨 추가 (관리자)
    @Transactional
    public TypingLessonResponseDto createLesson(TypingLessonRequestDto requestDto, HttpSession session) {
        checkAdminPermission(session);

        if (typingLessonRepository.findByTitle(requestDto.getTitle()).isPresent()) {
            throw new CustomException(ErrorCode.LESSON_ALREADY_EXISTS);
        }

        TypingLesson lesson = TypingLesson.builder()
                .title(requestDto.getTitle())
                .language(requestDto.getLanguage())
                .difficulty(requestDto.getDifficulty())
                .content(requestDto.getContent())
                .build();

        typingLessonRepository.save(lesson);

        return TypingLessonResponseDto.of("레슨 추가 완료", List.of(lesson));
    }

    // 레슨 업데이트 (관리자)
    @Transactional
    public TypingLessonResponseDto updateLesson(Long lessonId, TypingLessonRequestDto requestDto, HttpSession session) {
        checkAdminPermission(session);

        TypingLesson lesson = typingLessonRepository.findById(lessonId)
                .orElseThrow(() -> new CustomException(ErrorCode.LESSON_NOT_FOUND));

        lesson.updateLesson(requestDto.getTitle(), requestDto.getLanguage(), requestDto.getDifficulty(), requestDto.getContent());

        return TypingLessonResponseDto.of("레슨 수정 완료", List.of(lesson));
    }

    // 레슨 삭제 (관리자)
    @Transactional
    public void deleteLesson(Long lessonId, HttpSession session) {
        checkAdminPermission(session);

        TypingLesson lesson = typingLessonRepository.findById(lessonId)
                .orElseThrow(() -> new CustomException(ErrorCode.LESSON_NOT_FOUND));

        typingLessonRepository.delete(lesson);

        TypingLessonResponseDto.of("레슨 삭제 완료", null);
    }

    // 관리자 권한 확인 메소드
    private void checkAdminPermission(HttpSession session) {
        Object user = session.getAttribute("user");

        if (user == null || !((List<String>) session.getAttribute("roles")).contains("ADMIN")) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}
