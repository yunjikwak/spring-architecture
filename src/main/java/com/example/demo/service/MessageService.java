package com.example.demo.service;

import com.example.demo.controller.dto.MessageCreateRequestDto;
import com.example.demo.controller.dto.MessageResponseDto;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Message;
import com.example.demo.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageResponseDto save(MessageCreateRequestDto request) {
        // User 조회
        Integer userId = request.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("not exists"));
        Message message = Message.create(
                request.getContent(),
                user
        );
        Message created = messageRepository.save(message);
        return MessageResponseDto.from(created);
    }
}
