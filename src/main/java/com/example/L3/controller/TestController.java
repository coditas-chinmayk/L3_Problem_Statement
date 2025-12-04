package com.example.L3.controller;

import com.example.L3.dto.ApiResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/ping")
    public ApiResponseDto<Map<String, String>> ping() {
        return ApiResponseDto.ok("ok", Map.of("msg", "pong"));
    }
}
