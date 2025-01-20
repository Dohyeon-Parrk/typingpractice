package com.program.typingpractice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/example")
@RestController
public class TestController {

    @GetMapping("test")
    public void getTest() {
        log.info("test controller");
    }
}
