package com.program.typingpractice.global;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;


}
