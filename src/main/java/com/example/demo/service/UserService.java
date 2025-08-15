package com.example.demo.service;

import com.example.demo.controller.dto.UserCreateRequestDto;
import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Team;
import com.example.demo.repository.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @PostConstruct
    public void init() {
        Team A = teamRepository.save(Team.create("A Team"));
        Team B = teamRepository.save(Team.create("B Team"));

        User aaron = User.create("aaron", "123", "Aaron", 10, "DEVELOPER", "Backend");
        User baron = User.create("baron", "123", "Baron", 20, "DEVELOPER", "Frontend");
        User caron = User.create("caron", "123", "Caron", 30, "ENGINEER", "DevOps/SRE");

        aaron.assignTeam(A);
        baron.assignTeam(A);
        caron.assignTeam(B);

        userRepository.save(aaron);
        userRepository.save(baron);
        userRepository.save(caron);
    }

    public UserResponseDto findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저가 데이터베이스 내 존재하지 않습니다. 유저 id : " + id));
        return UserResponseDto.from(user);
    }

    public List<UserResponseDto> findByUsername(String username) {
        List<User> users = userRepository.findByName(username);
        return users.stream()
                .map(UserResponseDto::from)
                .toList();
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::from)
                .toList();
    }

    public UserResponseDto save(UserCreateRequestDto request) {
        User user = User.create(
                request.getUsername(),
                request.getPassword(),
                request.getName(),
                request.getAge(),
                request.getJob(),
                request.getSpecialty()
        );
        User created = userRepository.save(user);
        return UserResponseDto.from(created);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}