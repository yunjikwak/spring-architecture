package com.example.demo.repository;

import com.example.demo.repository.entity.User;

import java.util.List;

public interface IUserRepositoryCustom {
    List<User> findByName(String name);
}
