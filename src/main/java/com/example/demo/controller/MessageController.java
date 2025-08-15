package com.example.demo.controller;

import com.example.demo.controller.dto.MessageCreateRequestDto;
import com.example.demo.controller.dto.MessageResponseDto;
import com.example.demo.service.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageController {
    MessageService messageService;

    @PostMapping("")
    public ResponseEntity<MessageResponseDto> create(@RequestBody MessageCreateRequestDto request) {
        MessageResponseDto message = messageService.save(request);
        return ResponseEntity.ok(message);
    }
}
