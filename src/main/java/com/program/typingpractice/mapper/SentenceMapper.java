package com.program.typingpractice.mapper;

import com.program.typingpractice.model.Sentence;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SentenceMapper {
    Sentence getRandomSentence();
    List<Sentence> getAllSentences();
    void insertSentence(Sentence sentence);
    void updateSentence(Sentence sentence);
    void deleteSentence(int id);
}
