package com.program.typingpractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypingResult {
    private int id;
    private String userInput;
    private String correctInput;
    private double accuracy;
    private double timeTaken;
    private Timestamp createdAt;
}
