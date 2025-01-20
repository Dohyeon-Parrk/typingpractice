package com.program.typingpractice.service;

import com.program.typingpractice.mapper.TypingResultMapper;
import com.program.typingpractice.model.TypingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypingResultService {

    private final TypingResultMapper typingResultMapper;

    public void saveResult(TypingResult result) {
        typingResultMapper.insertTypingResult(result);
    }

    public List<TypingResult> getAllResults() {
        return typingResultMapper.getAllResults();
    }
}
