package com.program.typingpractice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.program.typingpractice.mapper")
public class TypingPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TypingPracticeApplication.class, args);
	}

}
