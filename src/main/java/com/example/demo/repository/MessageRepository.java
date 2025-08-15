package com.example.demo.repository;

import com.example.demo.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<Message> findById(Integer id);

}
