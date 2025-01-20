package com.program.typingpractice.service;

import com.program.typingpractice.mapper.SentenceMapper;
import com.program.typingpractice.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SentenceService {

    private final SentenceMapper sentenceMapper;

    public Sentence getRandomSentence() {
        return sentenceMapper.getRandomSentence();
    }

    public List<Sentence> getAllSentences() {
        return sentenceMapper.getAllSentences();
    }

    public void addSentence(Sentence sentence) {
        sentenceMapper.insertSentence(sentence);
    }

    public void updateSentence(Sentence sentence) {
        sentenceMapper.updateSentence(sentence);
    }

    public void deleteSentence(int id) {
        sentenceMapper.deleteSentence(id);
    }
}
