package com.program.typingpractice.domain.lesson;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String language; // ex: Java, Python

    @Column(nullable = false)
    private String difficulty; // Beginner, Intermediate, Advanced

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public Lesson(String title, String language, String difficulty, String content) {
        this.title = title;
        this.language = language;
        this.difficulty = difficulty;
        this.content = content;
    }
}
