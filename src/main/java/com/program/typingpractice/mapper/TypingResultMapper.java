package com.program.typingpractice.mapper;

import com.program.typingpractice.model.TypingResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypingResultMapper {
    void insertTypingResult(TypingResult typingResult);
    List<TypingResult> getAllResults();
}
