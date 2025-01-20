package com.program.typingpractice.controller;

import com.program.typingpractice.model.Sentence;
import com.program.typingpractice.model.TypingResult;
import com.program.typingpractice.service.SentenceService;
import com.program.typingpractice.service.TypingResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typing")
@RequiredArgsConstructor
public class TypingController {

    private final SentenceService sentenceService;
    private final TypingResultService typingResultService;

    @GetMapping("/sentence")
    public Sentence getRandomSentence() {
        return sentenceService.getRandomSentence();
    }

    @GetMapping("/sentences")
    public List<Sentence> getAllSentences() {
        return sentenceService.getAllSentences();
    }

    @PostMapping("/submit")
    public TypingResult submitTyping(@RequestBody TypingResult typingResult) {
        typingResultService.saveResult(typingResult);
        return typingResult;
    }

    @GetMapping("/results")
    public List<TypingResult> getAllResults() {
        return typingResultService.getAllResults();
    }
}
