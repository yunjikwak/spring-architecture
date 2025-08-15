package com.example.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageCreateRequestDto {
    private String content;
    private Integer userId;
}
