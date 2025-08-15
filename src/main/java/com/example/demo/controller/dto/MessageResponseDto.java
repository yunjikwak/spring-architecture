package com.example.demo.controller.dto;

import com.example.demo.repository.entity.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageResponseDto {
    private Integer id;
    private String content;

    public static MessageResponseDto from(Message entity) {
        return new MessageResponseDto(
                entity.getId(),
                entity.getContent()
        );
    }

}
